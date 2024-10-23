package com.vitalstats.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vitalstats.models.HealthFiles;
import com.vitalstats.models.HealthFolder;
import com.vitalstats.models.HealthRecords;

public interface RecordService {
	
	// HealthRecords methods
    HealthRecords saveHealthRecord(UUID userId);
    HealthRecords getAllHealthRecordsByUser(UUID userId);
    
    // HealthFolder methods
    HealthFolder saveHealthFolder(HealthFolder healthFolder, UUID userId);
    Optional<HealthFolder> getHealthFolderById(UUID folderId);
    List<HealthFolder> getAllHealthFoldersByUser(UUID userId);
    void deleteHealthFolder(UUID folderId);	

    // HealthFiles methods
    HealthFiles saveHealthFile(HealthFiles healthFile, UUID folderId) throws Exception;
    HealthFiles getHealthFileById(UUID healthFileId);
    List<HealthFiles> getAllHealthFilesByFolder(UUID folderId);
    void deleteHealthFile(UUID healthFileId);

}
