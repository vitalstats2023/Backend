package com.vitalstats.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthFileRequest {

	 private String fileName;
	 private UUID folderId;
}
