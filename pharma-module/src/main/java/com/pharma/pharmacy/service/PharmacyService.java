package com.pharma.pharmacy.service;

import com.pharma.pharmacy.dto.PharmacyDTO;
import com.pharma.pharmacy.entity.Pharmacy;
import com.pharma.pharmacy.repository.PharmacyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PharmacyService {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    public List<PharmacyDTO> getAllPharmacies() {
        return pharmacyRepository.findAllByOrderByIsMainPharmacyDescNameAsc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PharmacyDTO getPharmacyById(Long id) {
        return pharmacyRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public PharmacyDTO savePharmacy(PharmacyDTO dto) {
        if (Boolean.TRUE.equals(dto.getIsMainPharmacy())) {
            pharmacyRepository.findByIsMainPharmacyTrue().ifPresent(existing -> {
                throw new RuntimeException("Main pharmacy already exists: " + existing.getName() + ". Only one allowed.");
            });
        }
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(dto.getName());
        pharmacy.setLocation(dto.getLocation());
        pharmacy.setIsMainPharmacy(dto.getIsMainPharmacy() != null ? dto.getIsMainPharmacy() : false);
        Pharmacy saved = pharmacyRepository.save(pharmacy);
        return convertToDTO(saved);
    }

    public PharmacyDTO updatePharmacy(Long id, PharmacyDTO dto) {
        Pharmacy existing = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));

        if (Boolean.TRUE.equals(existing.getIsMainPharmacy()) && Boolean.FALSE.equals(dto.getIsMainPharmacy())) {
            throw new RuntimeException("Cannot remove main pharmacy status. Designate another pharmacy as main first.");
        }
        if (Boolean.TRUE.equals(dto.getIsMainPharmacy()) && !existing.getIsMainPharmacy()) {
            pharmacyRepository.findByIsMainPharmacyTrue().ifPresent(other -> {
                throw new RuntimeException("Main pharmacy already exists: " + other.getName() + ". Only one allowed.");
            });
        }

        existing.setName(dto.getName());
        existing.setLocation(dto.getLocation());
        if (dto.getIsMainPharmacy() != null) {
            existing.setIsMainPharmacy(dto.getIsMainPharmacy());
        }
        Pharmacy saved = pharmacyRepository.save(existing);
        return convertToDTO(saved);
    }

    public void deletePharmacy(Long id) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
        if (pharmacy.getIsMainPharmacy()) {
            throw new RuntimeException("Cannot delete main pharmacy.");
        }
        pharmacyRepository.delete(pharmacy);
    }

    private PharmacyDTO convertToDTO(Pharmacy pharmacy) {
        PharmacyDTO dto = new PharmacyDTO();
        dto.setId(pharmacy.getId());
        dto.setName(pharmacy.getName());
        dto.setLocation(pharmacy.getLocation());
        dto.setIsMainPharmacy(pharmacy.getIsMainPharmacy());
        return dto;
    }
}
