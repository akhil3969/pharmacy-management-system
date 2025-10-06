//package com.pharma.pharmacy.config;
//
////import com.pharmacycion.pharma.entity.*;
////import com.pharmacycion.pharma.repository.*;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.pharma.pharmacy.entity.Batch;
//import com.pharma.pharmacy.entity.Inventory;
//import com.pharma.pharmacy.entity.Medicine;
//import com.pharma.pharmacy.entity.Pharmacy;
//import com.pharma.pharmacy.repository.BatchRepository;
//import com.pharma.pharmacy.repository.InventoryRepository;
//import com.pharma.pharmacy.repository.MedicineRepository;
//import com.pharma.pharmacy.repository.PharmacyRepository;
//
//import java.time.LocalDate;
//
//@Configuration
//public class DataLoader {
//
//    @Bean
//    CommandLineRunner init(MedicineRepository medRepo,
//                           BatchRepository batchRepo,
//                           PharmacyRepository pharmacyRepo,
//                           InventoryRepository inventoryRepo) {
//        return args -> {
//            if (medRepo.count() == 0) {
//                Medicine p = new Medicine("Paracetamol", "Pain reliever");
//                medRepo.save(p);
//
//                Batch b1 = new Batch("BATCH-001", LocalDate.now().plusMonths(18), 10.0, 0);
//                b1.setMedicine(p);
//                batchRepo.save(b1);
//
//                Pharmacy ph1 = new Pharmacy("Central Pharmacy", "Main St");
//                pharmacyRepo.save(ph1);
//
//                Inventory inv = new Inventory(ph1, b1, 100);
//                inventoryRepo.save(inv);
//            }
//        };
//    }
//}
