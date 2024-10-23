package com.vitalstats.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthFolderRequest {
	
	 private String folderName;
	 private UUID userId;
	 private UUID folderId;

}
