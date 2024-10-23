package com.vitalstats.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.HealthFiles;
import com.vitalstats.models.HealthFolder;
import com.vitalstats.models.HealthRecords;

import jakarta.transaction.Transactional;

@Repository
public interface HealthFilesRepository extends JpaRepository<HealthFiles, UUID> {

    Optional<HealthFiles> findByUuid(UUID uuid);
    List<HealthFiles> findByHealthFolderUuid(UUID healthFolderUuid);
	HealthFolder save(HealthRecords records);
	
	 @Modifying
	 @Transactional
	 @Query("DELETE FROM HealthFiles m WHERE m.uuid = :fileId")
	 void deleteByUuid(@org.springframework.data.repository.query.Param("fileId") UUID fileId);
}
