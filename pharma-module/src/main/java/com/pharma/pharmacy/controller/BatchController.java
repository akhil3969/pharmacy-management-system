package com.pharma.pharmacy.controller;

import com.pharma.pharmacy.dto.BatchDTO;
import com.pharma.pharmacy.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batches")
@CrossOrigin(origins = "*")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @GetMapping
    public List<BatchDTO> getAllBatches() {
        return batchService.getAllBatches();
    }

    @PostMapping
    public BatchDTO createBatch(@RequestBody BatchDTO dto) {
        return batchService.saveBatch(dto);
    }

    @PutMapping("/{id}")
    public BatchDTO updateBatch(@PathVariable Long id, @RequestBody BatchDTO dto) {
        return batchService.updateBatch(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
    }
}
