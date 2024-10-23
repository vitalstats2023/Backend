package com.vitalstats.request;

import java.util.UUID;

import lombok.Data;

@Data
public class PrescriptionRequest {
	
	private UUID prescriptionId;
	private String fileName;
	private UUID userId;
}
