package com.vitalstats.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.HealthFolder;

@Repository
public interface HealthFolderRepository extends JpaRepository<HealthFolder, UUID> {

	List<HealthFolder> findAllByHealthRecordsUuid(UUID recordId);
}
