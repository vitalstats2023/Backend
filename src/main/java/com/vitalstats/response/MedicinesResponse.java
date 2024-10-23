package com.vitalstats.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vitalstats.models.Medicines;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicinesResponse {

	private UUID prescriptionId;
	private List<Medicines> medicines = new ArrayList<Medicines>();
}
