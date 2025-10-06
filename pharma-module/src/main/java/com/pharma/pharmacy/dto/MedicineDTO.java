package com.pharma.pharmacy.dto;

import java.util.List;

public class MedicineDTO {

    private Long id;
    private String name;
    private String description;
    private List<BatchDTO> batches;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<BatchDTO> getBatches() { return batches; }
    public void setBatches(List<BatchDTO> batches) { this.batches = batches; }
}
