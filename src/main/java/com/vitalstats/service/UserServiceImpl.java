package com.vitalstats.service;

import java.util.Optional;
import java.util.UUID;

import com.vitalstats.models.Family;
import com.vitalstats.models.Profile;
import com.vitalstats.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitalstats.models.User;
import com.vitalstats.repository.FamilyRepository;
import com.vitalstats.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FamilyRepository familyRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(UUID uuid) {
		Optional<User> user = userRepository.findByUuid(uuid);
		return user.get() ;
	}

	@Override
	public User getUserByAadhaar(String aadhaar) {
		Optional<User> user = userRepository.findByAadhaarNumber(aadhaar);
		return user.orElse( null);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}

	@Override
	public User getUserByPhoneNumber(String phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber).get();
	}

	@Override
	public User getUserByEmailOrPhoneNumber(String email, String phoneNumber) {
		return userRepository.findByEmailOrPhoneNumber(email, phoneNumber).orElse(null);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public boolean existsByAadhaarNumber(String aadhaarNumber) {
		return userRepository.existsByAadhaarNumber(aadhaarNumber);
	}

	@Override
	public Profile getProfileByUserId(UUID userId) {
		User user = getUserById(userId);
        return profileRepository.findByUser(user);
	}

	@Override
	public String generateNextVsid() {
	    Optional<User> user = userRepository.findTopByOrderByVsidDesc();
	    if (user.isPresent()) {
	        String vsid = user.get().getVsid();
	        if (vsid != null && !vsid.isEmpty()) {
	            int num = Integer.parseInt(vsid.substring(3)); 
	            return "VS-" + (num + 1);
	        }
	    }
	    return "VS-1";
	}

	@Override
	public Family addFamilyMember(UUID userId, User familyMember) {
		User user = getUserById(userId);
		Family family = familyRepository.findByUser(user);
		family.getMembers().add(familyMember);
		return familyRepository.save(family);
	}

	@Override
	public Family removeFamilyMember(UUID userId, UUID familyMemberId) {
		User user = getUserById(userId);
		Family family = familyRepository.findByUser(user);
		family.getMembers().removeIf(member -> member.getUuid().equals(familyMemberId));
		return familyRepository.save(family);
	}

	@Override
	public Profile saveProfile(Profile profile) {
		return profileRepository.save(profile);
	}

}
