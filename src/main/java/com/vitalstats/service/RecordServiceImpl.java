package com.vitalstats.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitalstats.models.HealthFiles;
import com.vitalstats.models.HealthFolder;
import com.vitalstats.models.HealthRecords;
import com.vitalstats.repository.HealthFilesRepository;
import com.vitalstats.repository.HealthFolderRepository;
import com.vitalstats.repository.HealthRecordsRepository;

@Service
public class RecordServiceImpl implements RecordService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private HealthRecordsRepository recordsRepository;
	@Autowired
	private HealthFolderRepository folderRepository;
	@Autowired
	private HealthFilesRepository filesRepository;
	

	@Override
	public HealthRecords saveHealthRecord(UUID userId) {
		HealthRecords records = new HealthRecords();
		records.setUser(userService.getUserById(userId));
		return recordsRepository.save(records) ;
	}

	@Override
	public HealthRecords getAllHealthRecordsByUser(UUID userId) {
		HealthRecords records = recordsRepository.findByUserUuid(userId);
		return records;
	}

	@Override
	public HealthFolder saveHealthFolder(HealthFolder healthFolder, UUID userId) {
		HealthRecords records = recordsRepository.findByUserUuid(userId);
		healthFolder.setHealthRecords(records);
		return folderRepository.save(healthFolder);
	}

	@Override
	public Optional<HealthFolder> getHealthFolderById(UUID folderId) {
		return Optional.ofNullable(folderRepository.findById(folderId).orElse(null));
	}

	@Override
	public List<HealthFolder> getAllHealthFoldersByUser(UUID userId) {
		UUID id = recordsRepository.findByUserUuid(userId).getUuid();
		return folderRepository.findAllByHealthRecordsUuid(id);
	}

	@Override
	public void deleteHealthFolder(UUID folderId) {
		folderRepository.deleteById(folderId);
	}

	@Override
	public HealthFiles saveHealthFile(HealthFiles healthFile, UUID folderId) throws Exception {
	    HealthFolder folder = folderRepository.findById(folderId)
	        .orElseThrow(() -> new Exception("HealthFolder with ID " + folderId + " not found"));

	    healthFile.setHealthFolder(folder);

	    return filesRepository.save(healthFile);
	}


	@Override
	public HealthFiles getHealthFileById(UUID healthFileId) {
		return filesRepository.findByUuid(healthFileId).get();
	}

	@Override
	public List<HealthFiles> getAllHealthFilesByFolder(UUID folderId) {
		return  filesRepository.findByHealthFolderUuid(folderId);
	}

	@Override
	public void deleteHealthFile(UUID healthFileId) {
		filesRepository.deleteByUuid(healthFileId);
	}

}
