package com.pharma.pharmacy.controller;

import com.pharma.pharmacy.dto.TransferDTO;
import com.pharma.pharmacy.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
@CrossOrigin(origins = "*")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping
    public List<TransferDTO> getAllTransfers() {
        return transferService.getAllTransfers();
    }

    @PostMapping
    public TransferDTO createTransfer(@RequestBody TransferDTO dto) {
        return transferService.createTransfer(dto);
    }
}
