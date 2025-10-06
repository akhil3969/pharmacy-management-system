package com.pharma.pharmacy.repository;

import com.pharma.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    Optional<Pharmacy> findByIsMainPharmacyTrue();
    List<Pharmacy> findByIsMainPharmacyFalse();
    List<Pharmacy> findAllByOrderByIsMainPharmacyDescNameAsc();
}
