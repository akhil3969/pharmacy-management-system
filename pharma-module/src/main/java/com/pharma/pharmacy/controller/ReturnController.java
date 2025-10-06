package com.pharma.pharmacy.controller;

import com.pharma.pharmacy.dto.ReturnDTO;
import com.pharma.pharmacy.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/returns")
@CrossOrigin(origins = "*")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @GetMapping
    public List<ReturnDTO> getAllReturns() {
        return returnService.getAllReturns();
    }

    @PostMapping
    public ReturnDTO createReturn(@RequestBody ReturnDTO dto) {
        return returnService.createReturn(dto);
    }
}
