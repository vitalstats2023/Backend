package com.vitalstats.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRequest {
	
	private UUID userId;
	private double height;
	private double weight;
	private String bloodGroup;
	private String profession;
	private List<String> diseases = new ArrayList<String>();
	private int movement;





}
