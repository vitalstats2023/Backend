package com.vitalstats.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vitalstats.models.MedicationTracker;
import com.vitalstats.models.Medicines;
import com.vitalstats.models.Prescription;

public interface MedicationService {

    // MedicationTracker methods
    MedicationTracker saveTracker(MedicationTracker tracker, UUID userId);
    Optional<MedicationTracker> getTrackerByUserId(UUID userId);

    // Prescription methods
    Prescription savePrescription(Prescription prescription);
    List<Prescription> getAllPrescriptionsByUser(UUID userId);
    Optional<Prescription> getPrescriptionById(UUID prescriptionId);
	void deletePrescription(UUID prescriptionId);

    // Medicines methods
    Medicines saveMedicine(Medicines medicine);
    List<Medicines> getAllMedicinesByPrescriptionId(UUID prescriptionId);
    Optional<Medicines> getMedicineById(UUID medicineId);
    void deleteMedicine(UUID medicineId);
}
