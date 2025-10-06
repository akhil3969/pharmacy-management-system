package com.pharma.pharmacy.controller;

import com.pharma.pharmacy.entity.PharmacyStock;
import com.pharma.pharmacy.repository.PharmacyStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/pharmacy-stock")
public class PharmacyStockController {

    @Autowired
    private PharmacyStockRepository pharmacyStockRepository;

    // GET /pharmacy-stock?batchId=1&pharmacyId=2
    @GetMapping
    public Map<String, Integer> getStock(@RequestParam Long batchId, @RequestParam Long pharmacyId) {
        Optional<PharmacyStock> stockOpt = pharmacyStockRepository.findByPharmacyIdAndBatchId(pharmacyId, batchId);
        int stock = stockOpt.map(PharmacyStock::getStock).orElse(0);
        return Map.of("stock", stock);
    }
}
