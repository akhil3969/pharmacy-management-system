package com.pharma.pharmacy.controller;

import com.pharma.pharmacy.dto.PharmacyDTO;
import com.pharma.pharmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacies")
@CrossOrigin(origins = "*")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping
    public List<PharmacyDTO> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
    }

    @GetMapping("/{id}")
    public PharmacyDTO getPharmacyById(@PathVariable Long id) {
        return pharmacyService.getPharmacyById(id);
    }

    @PostMapping
    public PharmacyDTO createPharmacy(@RequestBody PharmacyDTO dto) {
        return pharmacyService.savePharmacy(dto);
    }

    @PutMapping("/{id}")
    public PharmacyDTO updatePharmacy(@PathVariable Long id, @RequestBody PharmacyDTO dto) {
        return pharmacyService.updatePharmacy(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePharmacy(@PathVariable Long id) {
        pharmacyService.deletePharmacy(id);
    }
}
