package com.pharma.pharmacy.repository;

import com.pharma.pharmacy.entity.PharmacyStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PharmacyStockRepository extends JpaRepository<PharmacyStock, Long> {
    
    Optional<PharmacyStock> findByPharmacyIdAndBatchId(Long pharmacyId, Long batchId);
    
    List<PharmacyStock> findByPharmacyId(Long pharmacyId);
    
    List<PharmacyStock> findByBatchId(Long batchId);
}
