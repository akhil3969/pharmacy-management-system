package com.pharma.pharmacy.service;

import com.pharma.pharmacy.entity.PharmacyStock;
import com.pharma.pharmacy.repository.PharmacyStockRepository;
import com.pharma.pharmacy.repository.PharmacyRepository;
import com.pharma.pharmacy.repository.BatchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PharmacyStockService {

    @Autowired
    private PharmacyStockRepository pharmacyStockRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private BatchRepository batchRepository;

    public List<PharmacyStock> getStocksByPharmacyId(Long pharmacyId) {
        return pharmacyStockRepository.findByPharmacyId(pharmacyId);
    }

    public PharmacyStock getStockByPharmacyAndBatch(Long pharmacyId, Long batchId) {
        return pharmacyStockRepository.findByPharmacyIdAndBatchId(pharmacyId, batchId)
                .orElse(null);
    }

    public PharmacyStock saveStock(PharmacyStock stock) {
        return pharmacyStockRepository.save(stock);
    }

    // Helper method to decrease stock (for transfers/returns from source pharmacy)
    public void decreaseStock(Long pharmacyId, Long batchId, int quantity) {
        PharmacyStock stock = pharmacyStockRepository
                .findByPharmacyIdAndBatchId(pharmacyId, batchId)
                .orElseThrow(() -> new RuntimeException("Stock not found for pharmacy " + pharmacyId + " and batch " + batchId));
        
        if (stock.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + stock.getStock() + ", Required: " + quantity);
        }
        
        stock.setStock(stock.getStock() - quantity);
        pharmacyStockRepository.save(stock);
        System.out.println("Decreased stock by " + quantity + " for pharmacy " + pharmacyId + ", batch " + batchId + 
                         ". New stock: " + stock.getStock());
    }

    // Helper method to increase stock (for transfers/returns to destination pharmacy)
    public void increaseStock(Long pharmacyId, Long batchId, int quantity) {
        PharmacyStock stock = pharmacyStockRepository
                .findByPharmacyIdAndBatchId(pharmacyId, batchId)
                .orElseGet(() -> {
                    // Create new stock entry if doesn't exist
                    PharmacyStock newStock = new PharmacyStock();
                    newStock.setPharmacy(pharmacyRepository.findById(pharmacyId)
                            .orElseThrow(() -> new RuntimeException("Pharmacy not found: " + pharmacyId)));
                    newStock.setBatch(batchRepository.findById(batchId)
                            .orElseThrow(() -> new RuntimeException("Batch not found: " + batchId)));
                    newStock.setStock(0);
                    return newStock;
                });
        
        stock.setStock(stock.getStock() + quantity);
        pharmacyStockRepository.save(stock);
        System.out.println("Increased stock by " + quantity + " for pharmacy " + pharmacyId + ", batch " + batchId + 
                         ". New stock: " + stock.getStock());
    }
}
