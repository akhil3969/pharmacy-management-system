package com.pharma.pharmacy.dto;

public class PharmacyStockDTO {
    private Long pharmacyId;
    private String pharmacyName;
    private int stock;

    public PharmacyStockDTO() {}

    public PharmacyStockDTO(Long pharmacyId, String pharmacyName, int stock) {
        this.pharmacyId = pharmacyId;
        this.pharmacyName = pharmacyName;
        this.stock = stock;
    }

    // Getters and setters
    public Long getPharmacyId() { return pharmacyId; }
    public void setPharmacyId(Long pharmacyId) { this.pharmacyId = pharmacyId; }

    public String getPharmacyName() { return pharmacyName; }
    public void setPharmacyName(String pharmacyName) { this.pharmacyName = pharmacyName; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "PharmacyStockDTO{" +
                "pharmacyId=" + pharmacyId +
                ", pharmacyName='" + pharmacyName + '\'' +
                ", stock=" + stock +
                '}';
    }
}
