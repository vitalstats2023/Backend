package com.vitalstats.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUuid(UUID uuid);
    Optional<User> findByAadhaarNumber(String aadhaarNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByAadhaarNumber(String aadhaarNumber);
    Optional<User> findTopByOrderByVsidDesc();

}
