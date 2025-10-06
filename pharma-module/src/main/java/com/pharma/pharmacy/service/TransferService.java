package com.pharma.pharmacy.service;

import com.pharma.pharmacy.dto.TransferDTO;
import com.pharma.pharmacy.entity.Batch;
import com.pharma.pharmacy.entity.Pharmacy;
import com.pharma.pharmacy.entity.PharmacyStock;
import com.pharma.pharmacy.entity.Transfer;
import com.pharma.pharmacy.repository.BatchRepository;
import com.pharma.pharmacy.repository.PharmacyRepository;
import com.pharma.pharmacy.repository.PharmacyStockRepository;
import com.pharma.pharmacy.repository.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PharmacyStockRepository pharmacyStockRepository;

    @Autowired
    private PharmacyStockService pharmacyStockService; // ADD THIS

    public List<TransferDTO> getAllTransfers() {
        return transferRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransferDTO createTransfer(TransferDTO dto) {
        System.out.println("Creating transfer - Batch: " + dto.getBatchId() + 
                         ", From: " + dto.getFromPharmacyId() + 
                         ", To: " + dto.getToPharmacyId() + 
                         ", Quantity: " + dto.getQuantity());

        int quantity = dto.getQuantity();
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }
        if (dto.getFromPharmacyId().equals(dto.getToPharmacyId())) {
            throw new RuntimeException("Cannot transfer to the same pharmacy");
        }

        Batch batch = batchRepository.findById(dto.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        Pharmacy fromPharmacy = pharmacyRepository.findById(dto.getFromPharmacyId())
                .orElseThrow(() -> new RuntimeException("From pharmacy not found"));

        Pharmacy toPharmacy = pharmacyRepository.findById(dto.getToPharmacyId())
                .orElseThrow(() -> new RuntimeException("To pharmacy not found"));

        // Use PharmacyStockService helper methods for cleaner code
        try {
            pharmacyStockService.decreaseStock(dto.getFromPharmacyId(), dto.getBatchId(), quantity);
            pharmacyStockService.increaseStock(dto.getToPharmacyId(), dto.getBatchId(), quantity);
        } catch (RuntimeException e) {
            System.err.println("Transfer failed: " + e.getMessage());
            throw e;
        }

        // Save transfer record
        Transfer transfer = new Transfer();
        transfer.setQuantity(quantity);
        transfer.setBatch(batch);
        transfer.setFromPharmacy(fromPharmacy);
        transfer.setToPharmacy(toPharmacy);
        transfer = transferRepository.save(transfer);

        System.out.println("Transfer completed successfully with ID: " + transfer.getId());
        return convertToDTO(transfer);
    }

    private TransferDTO convertToDTO(Transfer transfer) {
        TransferDTO dto = new TransferDTO();
        dto.setId(transfer.getId());
        dto.setQuantity(transfer.getQuantity());
        dto.setBatchId(transfer.getBatch().getId());
        dto.setBatchNo(transfer.getBatch().getBatchNo());
        dto.setMedicineName(transfer.getBatch().getMedicine().getName());
        dto.setFromPharmacyId(transfer.getFromPharmacy().getId());
        dto.setFromPharmacyName(transfer.getFromPharmacy().getName());
        dto.setToPharmacyId(transfer.getToPharmacy().getId());
        dto.setToPharmacyName(transfer.getToPharmacy().getName());
        dto.setTransferDate(transfer.getTransferDate());
        return dto;
    }
}
