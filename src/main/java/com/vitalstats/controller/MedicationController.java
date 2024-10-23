package com.vitalstats.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitalstats.models.MedicationTracker;
import com.vitalstats.models.Medicines;
import com.vitalstats.models.Prescription;
import com.vitalstats.request.IdRequest;
import com.vitalstats.request.MedicineRequest;
import com.vitalstats.request.PrescriptionRequest;
import com.vitalstats.request.UserIdRequest;
import com.vitalstats.service.MedicationService;

@RestController
@RequestMapping("/api/medication")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @GetMapping("/trackerid")
    public ResponseEntity<MedicationTracker> getTrackerByUserId(@RequestBody UserIdRequest userIdRequest) {
        Optional<MedicationTracker> tracker = medicationService.getTrackerByUserId(userIdRequest.getUserId());
        return tracker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createprescription")
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionRequest request) {
        Optional<MedicationTracker> trackerOpt = medicationService.getTrackerByUserId(request.getUserId());
        if (trackerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        MedicationTracker tracker = trackerOpt.get();
        Prescription prescription = new Prescription();
        prescription.setName(request.getFileName());
        prescription.setMedicationTracker(tracker);

        Prescription savedPrescription = medicationService.savePrescription(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrescription);
    }

    @GetMapping("/viewallprescription")
    public ResponseEntity<List<Prescription>> viewAllPrescription(@RequestBody UserIdRequest request) {
        List<Prescription> prescriptions = medicationService.getAllPrescriptionsByUser(request.getUserId());
        return prescriptions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(prescriptions);
    }

    @PutMapping("/updateprescription")
    public ResponseEntity<Prescription> updatePrescription(@RequestBody PrescriptionRequest request) {
        Optional<Prescription> prescriptionOpt = medicationService.getPrescriptionById(request.getPrescriptionId());
        if (prescriptionOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Prescription prescription = prescriptionOpt.get();
        prescription.setName(request.getFileName());
        Prescription updatedPrescription = medicationService.savePrescription(prescription);

        return ResponseEntity.ok(updatedPrescription);
    }

    @DeleteMapping("/deleteprescription")
    public ResponseEntity<String> deletePrescriptions(@RequestBody IdRequest request) {
        medicationService.deletePrescription(request.getId());
        return ResponseEntity.ok("Deleted Successfully");
    }

    @PostMapping("/addmedicine")
    public ResponseEntity<List<Medicines>> addMedicines(@RequestBody MedicineRequest request) {
        Optional<Prescription> prescriptionOpt = medicationService.getPrescriptionById(request.getPrescriptionId());
        if (prescriptionOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Prescription prescription = prescriptionOpt.get();
        List<Medicines> savedMedicines = new ArrayList<>();

        for (Medicines medicine : request.getMedicines()) {
            medicine.setPrescription(prescription);
            Medicines savedMedicine = medicationService.saveMedicine(medicine);
            savedMedicines.add(savedMedicine);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMedicines);
    }

    @GetMapping("/viewallmedicines")
    public ResponseEntity<List<Medicines>> viewAllMedicines(@RequestBody IdRequest request) {
        List<Medicines> medicines = medicationService.getAllMedicinesByPrescriptionId(request.getId());
        return medicines.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(medicines);
    }

    @GetMapping("/viewmedicinebyid")
    public ResponseEntity<Medicines> viewMedicineById(@RequestBody IdRequest request) {
        Optional<Medicines> optional = medicationService.getMedicineById(request.getId());
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updatemedicine")
    public ResponseEntity<Medicines> updateMedicines(@RequestBody Medicines medicine) {
        Optional<Medicines> optionalMedicine = medicationService.getMedicineById(medicine.getUuid());
        if (optionalMedicine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Medicines existingMedicine = optionalMedicine.get();
        existingMedicine.setName(medicine.getName());
        existingMedicine.setStartDate(medicine.getStartDate());
        existingMedicine.setEndDate(medicine.getEndDate());
        existingMedicine.setFrequency(medicine.getFrequency());
        existingMedicine.setTime(medicine.getTime());
        Medicines updatedMedicine = medicationService.saveMedicine(existingMedicine);

        return ResponseEntity.ok(updatedMedicine);
    }

    @DeleteMapping("/deletemedicine")
    public ResponseEntity<String> deleteMedicine(@RequestBody IdRequest request) {
        medicationService.deleteMedicine(request.getId());
        return ResponseEntity.ok("Medicle Deleted");
    }
}
