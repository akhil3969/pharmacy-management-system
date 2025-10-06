package com.pharma.pharmacy.service;

import com.pharma.pharmacy.dto.ReturnDTO;
import com.pharma.pharmacy.entity.Batch;
import com.pharma.pharmacy.entity.Pharmacy;
import com.pharma.pharmacy.entity.Return;
import com.pharma.pharmacy.repository.BatchRepository;
import com.pharma.pharmacy.repository.PharmacyRepository;
import com.pharma.pharmacy.repository.ReturnRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReturnService {

    @Autowired
    private ReturnRepository returnRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PharmacyStockService pharmacyStockService; // ADD THIS

    public List<ReturnDTO> getAllReturns() {
        return returnRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReturnDTO createReturn(ReturnDTO dto) {
        System.out.println("Creating return - Batch: " + dto.getBatchId() + 
                         ", From: " + dto.getFromPharmacyId() + 
                         ", To: " + dto.getToPharmacyId() + 
                         ", Quantity: " + dto.getQuantity());

        int quantity = dto.getQuantity();
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }
        if (dto.getFromPharmacyId().equals(dto.getToPharmacyId())) {
            throw new RuntimeException("Cannot return to the same pharmacy");
        }

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Pharmacy fromPharmacy = pharmacyRepository.findById(dto.getFromPharmacyId())
                .orElseThrow(() -> new RuntimeException("From pharmacy not found"));

        Pharmacy toPharmacy = pharmacyRepository.findById(dto.getToPharmacyId())
                .orElseThrow(() -> new RuntimeException("To pharmacy not found"));

        // Use PharmacyStockService helper methods
        try {
            pharmacyStockService.decreaseStock(dto.getFromPharmacyId(), dto.getBatchId(), quantity);
            pharmacyStockService.increaseStock(dto.getToPharmacyId(), dto.getBatchId(), quantity);
        } catch (RuntimeException e) {
            System.err.println("Return failed: " + e.getMessage());
            throw e;
        }

        // Save return record
        Return ret = new Return();
        ret.setQuantity(quantity);
        ret.setBatch(batch);
        ret.setFromPharmacy(fromPharmacy);
        ret.setToPharmacy(toPharmacy);
        ret = returnRepository.save(ret);

        System.out.println("Return completed successfully with ID: " + ret.getId());
        return convertToDTO(ret);
    }

    private ReturnDTO convertToDTO(Return ret) {
        ReturnDTO dto = new ReturnDTO();
        dto.setId(ret.getId());
        dto.setQuantity(ret.getQuantity());
        dto.setBatchId(ret.getBatch().getId());
        dto.setBatchNo(ret.getBatch().getBatchNo());
        dto.setMedicineName(ret.getBatch().getMedicine().getName());
        dto.setFromPharmacyId(ret.getFromPharmacy().getId());
        dto.setFromPharmacyName(ret.getFromPharmacy().getName());
        dto.setToPharmacyId(ret.getToPharmacy().getId());
        dto.setToPharmacyName(ret.getToPharmacy().getName());
        dto.setReturnDate(ret.getReturnDate());
        return dto;
    }
}
