package com.vitalstats.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsReqest {

	private String aadhaarNumber;
	private String otp;
	private String gmail;
	private String phoneNumber;
	private String password;


}
