package com.pharma.pharmacy.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime transferDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_pharmacy_id", nullable = false)
    private Pharmacy fromPharmacy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_pharmacy_id", nullable = false)
    private Pharmacy toPharmacy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDateTime transferDate) {
		this.transferDate = transferDate;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Pharmacy getFromPharmacy() {
		return fromPharmacy;
	}

	public void setFromPharmacy(Pharmacy fromPharmacy) {
		this.fromPharmacy = fromPharmacy;
	}

	public Pharmacy getToPharmacy() {
		return toPharmacy;
	}

	public void setToPharmacy(Pharmacy toPharmacy) {
		this.toPharmacy = toPharmacy;
	}

    // Getters and setters...
}
