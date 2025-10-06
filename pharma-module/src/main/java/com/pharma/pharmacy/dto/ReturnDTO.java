package com.pharma.pharmacy.dto;

import java.time.LocalDateTime;

public class ReturnDTO {

    private Long id;
    private Integer quantity;
    private LocalDateTime returnDate;
    private Long batchId;
    private String batchNo;
    private String medicineName;
    private Long fromPharmacyId;
    private String fromPharmacyName;
    private Long toPharmacyId;
    private String toPharmacyName;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public Long getBatchId() { return batchId; }
    public void setBatchId(Long batchId) { this.batchId = batchId; }
    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public Long getFromPharmacyId() { return fromPharmacyId; }
    public void setFromPharmacyId(Long fromPharmacyId) { this.fromPharmacyId = fromPharmacyId; }
    public String getFromPharmacyName() { return fromPharmacyName; }
    public void setFromPharmacyName(String fromPharmacyName) { this.fromPharmacyName = fromPharmacyName; }
    public Long getToPharmacyId() { return toPharmacyId; }
    public void setToPharmacyId(Long toPharmacyId) { this.toPharmacyId = toPharmacyId; }
    public String getToPharmacyName() { return toPharmacyName; }
    public void setToPharmacyName(String toPharmacyName) { this.toPharmacyName = toPharmacyName; }
}
