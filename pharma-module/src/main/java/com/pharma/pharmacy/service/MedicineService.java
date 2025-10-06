package com.pharma.pharmacy.service;

import com.pharma.pharmacy.dto.BatchDTO;
import com.pharma.pharmacy.dto.MedicineDTO;
import com.pharma.pharmacy.dto.PharmacyStockDTO;
import com.pharma.pharmacy.entity.Batch;
import com.pharma.pharmacy.entity.Medicine;
import com.pharma.pharmacy.entity.Pharmacy;
import com.pharma.pharmacy.entity.PharmacyStock;
import com.pharma.pharmacy.repository.BatchRepository;
import com.pharma.pharmacy.repository.MedicineRepository;
import com.pharma.pharmacy.repository.PharmacyRepository;
import com.pharma.pharmacy.repository.PharmacyStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PharmacyStockRepository pharmacyStockRepository;

    public List<MedicineDTO> getAllMedicines() {
        List<Medicine> medicines = medicineRepository.findAll();
        System.out.println("Found " + medicines.size() + " medicines");
        return medicines.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MedicineDTO getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public MedicineDTO saveMedicine(MedicineDTO dto) {
        System.out.println("Saving medicine: " + dto.getName());
        
        Medicine medicine = new Medicine();
        medicine.setName(dto.getName());
        medicine.setDescription(dto.getDescription());
        medicine = medicineRepository.save(medicine);
        System.out.println("Saved medicine with ID: " + medicine.getId());

        Pharmacy mainPharmacy = pharmacyRepository.findByIsMainPharmacyTrue()
                .orElseThrow(() -> new RuntimeException("Main Pharmacy not found. Please create one first."));
        System.out.println("Main pharmacy ID: " + mainPharmacy.getId());

        if (dto.getBatches() != null && !dto.getBatches().isEmpty()) {
            List<Batch> savedBatches = new ArrayList<>();

            for (BatchDTO batchDTO : dto.getBatches()) {
                System.out.println("Processing batch: " + batchDTO.getBatchNo() + " with stock: " + batchDTO.getStock());
                
                Batch batch = new Batch();
                batch.setBatchNo(batchDTO.getBatchNo());
                batch.setExpiryDate(batchDTO.getExpiryDate());
                batch.setPrice(batchDTO.getPrice());
                batch.setMedicine(medicine);
                Batch savedBatch = batchRepository.save(batch);
                savedBatches.add(savedBatch);
                System.out.println("Saved batch with ID: " + savedBatch.getId());

                // Create pharmacy stock
                PharmacyStock stock = new PharmacyStock();
                stock.setPharmacy(mainPharmacy);
                stock.setBatch(savedBatch);
                stock.setStock(batchDTO.getStock());
                PharmacyStock savedStock = pharmacyStockRepository.save(stock);
                System.out.println("Saved pharmacy stock - ID: " + savedStock.getId() + 
                                 ", Pharmacy: " + savedStock.getPharmacy().getId() + 
                                 ", Batch: " + savedStock.getBatch().getId() + 
                                 ", Stock: " + savedStock.getStock());
            }
            medicine.setBatches(savedBatches);
        }

        return convertToDTO(medicine);
    }

    public MedicineDTO updateMedicine(Long id, MedicineDTO dto) {
        Medicine existing = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());

        existing = medicineRepository.save(existing);
        return convertToDTO(existing);
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    private MedicineDTO convertToDTO(Medicine medicine) {
        MedicineDTO dto = new MedicineDTO();
        dto.setId(medicine.getId());
        dto.setName(medicine.getName());
        dto.setDescription(medicine.getDescription());

        if (medicine.getBatches() != null) {
            List<BatchDTO> batchDTOs = medicine.getBatches().stream().map(batch -> {
                System.out.println("Converting batch ID: " + batch.getId() + " (" + batch.getBatchNo() + ")");
                
                BatchDTO b = new BatchDTO();
                b.setId(batch.getId());
                b.setBatchNo(batch.getBatchNo());
                b.setExpiryDate(batch.getExpiryDate());
                b.setPrice(batch.getPrice());
                b.setMedicineId(medicine.getId());

                // Fetch pharmacy stocks for this batch
                List<PharmacyStock> stocks = pharmacyStockRepository.findByBatchId(batch.getId());
                System.out.println("Found " + stocks.size() + " stock records for batch " + batch.getId());

                List<PharmacyStockDTO> stockDTOs = stocks.stream().map(stock -> {
                    PharmacyStockDTO psDto = new PharmacyStockDTO();
                    psDto.setPharmacyId(stock.getPharmacy().getId());
                    psDto.setPharmacyName(stock.getPharmacy().getName());
                    psDto.setStock(stock.getStock());
                    System.out.println("Stock for pharmacy '" + stock.getPharmacy().getName() + "' (ID: " + 
                                     stock.getPharmacy().getId() + "): " + stock.getStock());
                    return psDto;
                }).collect(Collectors.toList());

                b.setPharmacyStocks(stockDTOs);

                return b;
            }).collect(Collectors.toList());
            dto.setBatches(batchDTOs);
        }
        return dto;
    }
}
