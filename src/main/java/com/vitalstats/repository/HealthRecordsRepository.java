package com.vitalstats.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.HealthRecords;

@Repository
public interface HealthRecordsRepository extends JpaRepository<HealthRecords, UUID> {

    Optional<HealthRecords> findByUuid(UUID uuid);
    HealthRecords findByUserUuid(UUID userUuid);
    void deleteByUuid(UUID uuid);
}
