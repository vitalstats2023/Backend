package com.vitalstats.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitalstats.models.MedicationTracker;
import com.vitalstats.models.Profile;
import com.vitalstats.models.User;
import com.vitalstats.request.DetailsReqest;
import com.vitalstats.request.EncryptedData;
import com.vitalstats.request.IdRequest;
import com.vitalstats.request.LoginRequest;
import com.vitalstats.request.SurveyRequest;
import com.vitalstats.service.MedicationService;
import com.vitalstats.service.RecordService;
import com.vitalstats.service.SendEmailService;
import com.vitalstats.service.UserService;
import com.vitalstats.utils.AesEncryptor;
import com.vitalstats.utils.Utils;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MedicationService medicationService;
    
    @Autowired
    private RecordService recordService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SendEmailService sendEmailService;
    
    
    @PostMapping("/getaadhaar")
    public ResponseEntity<?> getAadhaar(@RequestBody EncryptedData encryptedData) throws Exception {

        String aadhaarNo = AesEncryptor.decrypt(encryptedData.getData());
        if(userService.existsByAadhaarNumber(aadhaarNo)) {

            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Aadhaar number already exists.");
        }
    	//Initiate Session
    	
    	// opt call
    	
    	
    	return ResponseEntity.ok(Utils.generateOtp());
    }
    
    
    @PostMapping("/verifyotp")
    public ResponseEntity<?> verifyOtp(@RequestBody EncryptedData encryptedData) throws Exception {

        String plaintext = AesEncryptor.decrypt(encryptedData.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        DetailsReqest detailsReqest = objectMapper.readValue(plaintext, DetailsReqest.class);
        User user = new User();
        user.setAadhaarNumber(detailsReqest.getAadhaarNumber());
        user.setEmail(detailsReqest.getGmail());
        user.setPhoneNumber(detailsReqest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(detailsReqest.getPassword()));
        user.setVsid(userService.generateNextVsid());
        
        userService.saveUser(user);
    	
    	return ResponseEntity.status(HttpStatus.CREATED).body("User Created SucessFully");
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userService.existsByAadhaarNumber(user.getAadhaarNumber())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Aadhaar number already exists.");
            }
            user.setPassword(passwordEncoder.encode("Lalitesh@2004"));
            user.setVsid(userService.generateNextVsid());
            user.setEmail("Lalitesh.mupparaju04@gmail.com");
            user.setPhoneNumber("9502211865");
            User savedUser = userService.saveUser(user);
            setDetials(savedUser.getUuid());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during registration: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EncryptedData encryptedData) {
        String data = encryptedData.getData();
        System.out.println(data);
        try {
            // Decrypt the encrypted string using the AesEncryptor class
            String decryptedData = AesEncryptor.decrypt(data);

            // Use ObjectMapper to convert decrypted JSON string into a LoginRequest object
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = objectMapper.readValue(decryptedData, LoginRequest.class);

            // Now, proceed with your user authentication logic
            User user = userService.getUserByEmailOrPhoneNumber(loginRequest.getUsername(), loginRequest.getUsername());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during login: " + e.getMessage());
        }
    }
    
    public void setDetials(UUID userId) {
    	medicationService.saveTracker(new MedicationTracker(), userId);
    	recordService.saveHealthRecord(userId);
    }
    


    @PostMapping("forgetpassword")
    public ResponseEntity<?> forgetPassword(@RequestBody EncryptedData encryptedData) throws Exception {
        String data = encryptedData.getData();
        String plaintext = AesEncryptor.decrypt(data);
        IdRequest idRequest = new ObjectMapper().readValue(plaintext, IdRequest.class);
        User user = userService.getUserByEmail(idRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        int otp = Utils.generateOtp();
        Profile profile= userService.getProfileByUserId(idRequest.getId());
        String emailSubject = "Forget Password - OTP";
        String emailBody = "Dear " + profile.getName() + ",\n\nYour OTP for password reset is: " + otp + ".\nPlease use this to reset your password.\n\nBest regards,\nYour App Team";
        sendEmailService.sendEmail(user.getEmail(), emailSubject, emailBody);
        return ResponseEntity.ok("OTP sent successfully to " + user.getEmail());
    }
    
    @PostMapping("changepassword")
    public ResponseEntity<?> changePassword(@RequestBody EncryptedData encryptedData) throws Exception {
        String data = encryptedData.getData();
        String plaintext = AesEncryptor.decrypt(data);
        IdRequest idRequest = new ObjectMapper().readValue(plaintext, IdRequest.class);
        User user=userService.getUserById(idRequest.getId());
        user.setPassword((passwordEncoder.encode(idRequest.getPassword())));
        userService.saveUser(user);
        return ResponseEntity.ok("Password changed successfully");
    }
    
    @PostMapping("survey")
    public ResponseEntity<?> Survey(@RequestBody EncryptedData encryptedData) throws Exception {
    	
    	String plaintext= AesEncryptor.decrypt(encryptedData.getData());
    	SurveyRequest surveyRequest = new ObjectMapper().readValue(plaintext, SurveyRequest.class);
    	Profile profile = new Profile();
    	profile.setUser(userService.getUserById(surveyRequest.getUserId()));
    	profile.setHeight(surveyRequest.getHeight());
    	profile.setWeight(surveyRequest.getWeight());
    	profile.setBloodGrp(surveyRequest.getBloodGroup());
    	profile.setProfession(surveyRequest.getProfession());
    	profile.setChronicdieases(surveyRequest.getDiseases());
    	profile.setRoutinemovement(surveyRequest.getMovement());
    	userService.saveProfile(profile);
        return ResponseEntity.ok("Survey Done Ready to Go") ;
    }

    @PostMapping("addfamily")
    public ResponseEntity<?> addFamily(@RequestBody EncryptedData encryptedData) throws Exception {

        return  null;
    }

    @PostMapping("familyverifyotp")
    public ResponseEntity<?> familyVerifyOtp(@RequestBody EncryptedData encryptedData) throws Exception {
        String otp = AesEncryptor.decrypt(encryptedData.getData());
        //Aadhaar Api call;
        return  ResponseEntity.ok(otp);
    }

}
