package com.vitalstats.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    Optional<Prescription> findByUuid(UUID uuid);  // Finds prescription by UUID
    List<Prescription> findByMedicationTrackerUuid(UUID uuid);  // Finds prescriptions by tracker UUID

    // Corrected method to find by prescription UUID and user UUID
    Optional<Prescription> findByUuidAndMedicationTrackerUserUuid(UUID prescriptionUuid, UUID userUuid);

    // Find all prescriptions by user UUID
    List<Prescription> findAllByMedicationTrackerUserUuid(UUID userUuid);
}
