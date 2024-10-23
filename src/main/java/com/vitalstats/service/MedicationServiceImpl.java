package com.vitalstats.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitalstats.models.MedicationTracker;
import com.vitalstats.models.Medicines;
import com.vitalstats.models.Prescription;
import com.vitalstats.models.User;
import com.vitalstats.repository.MedicationTrackerRepository;
import com.vitalstats.repository.MedicinesRepository;
import com.vitalstats.repository.PrescriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationTrackerRepository medicationTrackerRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicinesRepository medicinesRepository;

    @Autowired
    private UserService userService;

    // MedicationTracker methods
    @Override
    public MedicationTracker saveTracker(MedicationTracker tracker, UUID userId) {
        // Fetch user from userId
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(userId));
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        User user = userOptional.get();

        // Check if the tracker already exists for this user
        Optional<MedicationTracker> existingTracker = Optional.ofNullable(medicationTrackerRepository.findByUserUuid(userId));
        if (existingTracker.isPresent()) {
            throw new IllegalArgumentException("Tracker already exists for this user.");
        }

        // Set the user and save the tracker
        tracker.setUser(user);
        return medicationTrackerRepository.save(tracker);
    }

    @Override
    
    public Optional<MedicationTracker> getTrackerByUserId(UUID userId) {
        return Optional.ofNullable(medicationTrackerRepository.findByUserUuid(userId));
    }


    @Override
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public List<Prescription> getAllPrescriptionsByUser(UUID userId) {
        return prescriptionRepository.findAllByMedicationTrackerUserUuid(userId);
    }

    @Override
    public Optional<Prescription> getPrescriptionById(UUID prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }

    @Override
    @Transactional
    public void deletePrescription(UUID prescriptionId) {
       prescriptionRepository.deleteById(prescriptionId);
    }

    // Medicines methods
    @Override
    public Medicines saveMedicine(Medicines medicine) {
        return medicinesRepository.save(medicine);
    }

    @Override
    public List<Medicines> getAllMedicinesByPrescriptionId(UUID prescriptionId) {
        return medicinesRepository.findByPrescriptionUuid(prescriptionId);
    }

    @Override
    public Optional<Medicines> getMedicineById(UUID medicineId) {
        return medicinesRepository.findById(medicineId);
    }
    
    @Override
    @Transactional
    public void deleteMedicine(UUID medicineId) {
        medicinesRepository.deleteByUuid(medicineId);
    }
    
    
}
