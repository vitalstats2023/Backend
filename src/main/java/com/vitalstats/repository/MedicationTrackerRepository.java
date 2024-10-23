package com.vitalstats.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.MedicationTracker;
import com.vitalstats.models.User;

@Repository
public interface MedicationTrackerRepository extends JpaRepository<MedicationTracker, UUID> {

    Optional<MedicationTracker> findByUuid(UUID uuid);
    MedicationTracker findByUserUuid(UUID userUuid);
    List<MedicationTracker> findByUser(User user);

}
