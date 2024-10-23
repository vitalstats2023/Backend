package com.vitalstats.service;

import java.util.UUID;

import com.vitalstats.models.Family;
import com.vitalstats.models.Profile;
import com.vitalstats.models.User;

public interface UserService {
	
	public User saveUser(User user);
	public User getUserById(UUID uuid);
	public User getUserByAadhaar(String aadhaar);
	public User getUserByEmail(String email);
	public User getUserByPhoneNumber(String phoneNumber);
	public User getUserByEmailOrPhoneNumber(String email, String phoneNumber);
	public boolean existsByEmail(String email);
	public boolean existsByPhoneNumber(String phoneNumber);
	public boolean existsByAadhaarNumber(String aadhaarNumber);
	
	public Profile getProfileByUserId(UUID userId);
	public Profile saveProfile(Profile profile);
	public String generateNextVsid();
	public Family addFamilyMember(UUID userId, User familyMember);
	public Family removeFamilyMember(UUID userId,UUID familyMemberId);
}
