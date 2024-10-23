package com.vitalstats.repository;

import com.vitalstats.models.Profile;
import com.vitalstats.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Profile findByUser(User user);
}
