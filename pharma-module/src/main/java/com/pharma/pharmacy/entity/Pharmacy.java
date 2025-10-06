package com.pharma.pharmacy.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean isMainPharmacy = false;

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<PharmacyStock> stocks;

    @OneToMany(mappedBy = "fromPharmacy", cascade = CascadeType.ALL)
    private List<Transfer> transfersFrom;

    @OneToMany(mappedBy = "toPharmacy", cascade = CascadeType.ALL)
    private List<Transfer> transfersTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getIsMainPharmacy() {
		return isMainPharmacy;
	}

	public void setIsMainPharmacy(Boolean isMainPharmacy) {
		this.isMainPharmacy = isMainPharmacy;
	}

	public List<PharmacyStock> getStocks() {
		return stocks;
	}

	public void setStocks(List<PharmacyStock> stocks) {
		this.stocks = stocks;
	}

	public List<Transfer> getTransfersFrom() {
		return transfersFrom;
	}

	public void setTransfersFrom(List<Transfer> transfersFrom) {
		this.transfersFrom = transfersFrom;
	}

	public List<Transfer> getTransfersTo() {
		return transfersTo;
	}

	public void setTransfersTo(List<Transfer> transfersTo) {
		this.transfersTo = transfersTo;
	}

    // Getters and setters...
}
