package com.pharma.pharmacy.repository;

import com.pharma.pharmacy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {}
