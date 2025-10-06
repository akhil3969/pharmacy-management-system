package com.pharma.pharmacy.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BatchDTO {

    private Long id;
    private String batchNo;
    private LocalDate expiryDate;
    private BigDecimal price;
    private Long medicineId;
    private int stock; // For input when creating batches
    private List<PharmacyStockDTO> pharmacyStocks; // For output when fetching batches

    // Default constructor
    public BatchDTO() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public List<PharmacyStockDTO> getPharmacyStocks() { return pharmacyStocks; }
    public void setPharmacyStocks(List<PharmacyStockDTO> pharmacyStocks) { this.pharmacyStocks = pharmacyStocks; }

    // Helper method to get total stock across all pharmacies
    public Integer getTotalStock() {
        if (pharmacyStocks == null || pharmacyStocks.isEmpty()) {
            return stock; // Return the input stock if no pharmacy stocks
        }
        return pharmacyStocks.stream()
                .mapToInt(PharmacyStockDTO::getStock)
                .sum();
    }

    @Override
    public String toString() {
        return "BatchDTO{" +
                "id=" + id +
                ", batchNo='" + batchNo + '\'' +
                ", expiryDate=" + expiryDate +
                ", price=" + price +
                ", stock=" + stock +
                ", pharmacyStocks=" + pharmacyStocks +
                '}';
    }
}
