package com.pharma.pharmacy.service;

import com.pharma.pharmacy.dto.BatchDTO;
import com.pharma.pharmacy.dto.PharmacyStockDTO;
import com.pharma.pharmacy.entity.Batch;
import com.pharma.pharmacy.entity.Medicine;
import com.pharma.pharmacy.entity.PharmacyStock;
import com.pharma.pharmacy.repository.BatchRepository;
import com.pharma.pharmacy.repository.MedicineRepository;
import com.pharma.pharmacy.repository.PharmacyStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PharmacyStockRepository pharmacyStockRepository;

    public List<BatchDTO> getAllBatches() {
        return batchRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BatchDTO getBatchById(Long id) {
        return batchRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public BatchDTO saveBatch(BatchDTO dto) {
        Batch batch = convertToEntity(dto);
        return convertToDTO(batchRepository.save(batch));
    }

    public BatchDTO updateBatch(Long id, BatchDTO dto) {
        Batch existing = batchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        existing.setBatchNo(dto.getBatchNo());
        existing.setExpiryDate(dto.getExpiryDate());
        existing.setPrice(dto.getPrice());

        if (dto.getMedicineId() != null) {
            Medicine medicine = medicineRepository.findById(dto.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found"));
            existing.setMedicine(medicine);
        }

        return convertToDTO(batchRepository.save(existing));
    }

    public void deleteBatch(Long id) {
        batchRepository.deleteById(id);
    }

    private BatchDTO convertToDTO(Batch batch) {
        BatchDTO dto = new BatchDTO();
        dto.setId(batch.getId());
        dto.setBatchNo(batch.getBatchNo());
        dto.setExpiryDate(batch.getExpiryDate());
        dto.setPrice(batch.getPrice());
        dto.setMedicineId(batch.getMedicine() != null ? batch.getMedicine().getId() : null);
        
        // Populate pharmacy stocks
        List<PharmacyStock> stocks = pharmacyStockRepository.findByBatchId(batch.getId());
        List<PharmacyStockDTO> stockDTOs = stocks.stream().map(stock -> {
            PharmacyStockDTO psDto = new PharmacyStockDTO();
            psDto.setPharmacyId(stock.getPharmacy().getId());
            psDto.setPharmacyName(stock.getPharmacy().getName());
            psDto.setStock(stock.getStock());
            return psDto;
        }).collect(Collectors.toList());
        
        dto.setPharmacyStocks(stockDTOs);
        
        return dto;
    }

    private Batch convertToEntity(BatchDTO dto) {
        Batch batch = new Batch();
        batch.setBatchNo(dto.getBatchNo());
        batch.setExpiryDate(dto.getExpiryDate());
        batch.setPrice(dto.getPrice());

        if (dto.getMedicineId() != null) {
            Medicine medicine = medicineRepository.findById(dto.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found"));
            batch.setMedicine(medicine);
        }
        return batch;
    }
}
