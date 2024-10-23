package com.vitalstats.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jakarta.persistence.AttributeConverter;

public class AesEncryptor implements AttributeConverter<String, String> {

	private static final String SECRET_KEY = "12345678901234567890123456789012"; 

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null; 
        }
        try {
            return encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during encryption", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null; // or handle as needed
        }
        try {
            return decrypt(dbData);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during decryption", e);
        }
    }

    public static String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, "UTF-8");
    }
}
