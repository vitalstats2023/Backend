package com.vitalstats.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.Family;
import com.vitalstats.models.User;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Family findByUuid(UUID uuid);

    Family findByUser(User user);
}
