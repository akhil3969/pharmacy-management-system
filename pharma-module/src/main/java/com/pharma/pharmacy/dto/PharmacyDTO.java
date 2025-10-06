package com.pharma.pharmacy.dto;

public class PharmacyDTO {

    private Long id;
    private String name;
    private String location;
    private Boolean isMainPharmacy;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Boolean getIsMainPharmacy() { return isMainPharmacy; }
    public void setIsMainPharmacy(Boolean mainPharmacy) { isMainPharmacy = mainPharmacy; }
}
