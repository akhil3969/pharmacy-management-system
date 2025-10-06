package com.pharma.pharmacy.repository;

import com.pharma.pharmacy.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Long> {}
