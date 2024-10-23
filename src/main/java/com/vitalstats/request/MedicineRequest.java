package com.vitalstats.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vitalstats.models.Medicines;

import lombok.Data;

@Data
public class MedicineRequest {
	
	private UUID prescriptionId;
	List<Medicines> medicines = new ArrayList<Medicines>(); 
	
}
