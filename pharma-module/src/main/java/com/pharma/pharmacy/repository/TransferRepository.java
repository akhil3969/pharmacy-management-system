package com.pharma.pharmacy.repository;

import com.pharma.pharmacy.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByFromPharmacyIdOrToPharmacyId(Long fromPharmacyId, Long toPharmacyId);
    List<Transfer> findByFromPharmacyId(Long fromPharmacyId);
    List<Transfer> findByToPharmacyId(Long toPharmacyId);
    List<Transfer> findByBatchId(Long batchId);

    @Query("SELECT t FROM Transfer t ORDER BY t.transferDate DESC")
    List<Transfer> findAllOrderByTransferDateDesc();
}
