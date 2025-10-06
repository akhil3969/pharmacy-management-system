package com.pharma.pharmacy.controller;

import com.pharma.pharmacy.dto.MedicineDTO;
import com.pharma.pharmacy.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public List<MedicineDTO> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/{id}")
    public MedicineDTO getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @PostMapping
    public MedicineDTO createMedicine(@RequestBody MedicineDTO dto) {
        return medicineService.saveMedicine(dto);
    }

    @PutMapping("/{id}")
    public MedicineDTO updateMedicine(@PathVariable Long id, @RequestBody MedicineDTO dto) {
        return medicineService.updateMedicine(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }
}
