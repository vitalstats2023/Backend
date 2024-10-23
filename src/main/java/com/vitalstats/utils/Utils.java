package com.vitalstats.utils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class Utils {
	
	 public static int generateOtp() {
		 SecureRandom secureRandom = new SecureRandom();
	     int otp = secureRandom.nextInt(900000) + 100000;
	     return otp;
	 }

	 public static String encodeImage(MultipartFile file) throws IOException {
		 
		 if (file.isEmpty()) {
	         return "File is empty";
	     }
	     String fileType = file.getContentType();
	     byte[] fileContent = file.getBytes();
	     String encodedString = Base64.getEncoder().encodeToString(fileContent);
	     return	"data:" + fileType + ";base64," + encodedString;
	 }
}
