package com.vitalstats.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vitalstats.models.Medicines;

import jakarta.transaction.Transactional;

@Repository
public interface MedicinesRepository extends JpaRepository<Medicines, UUID> {

    List<Medicines> findByPrescriptionUuid(UUID prescriptionUuid); 
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Medicines m WHERE m.uuid = :medicineUuid")
    void deleteByUuid(@org.springframework.data.repository.query.Param("medicineUuid") UUID medicineUuid);

}
