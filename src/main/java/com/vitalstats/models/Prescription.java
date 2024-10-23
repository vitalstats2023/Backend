package com.vitalstats.models;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitalstats.utils.AesEncryptor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Prescription {
    
    @Id
    @Column(columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID uuid = UUID.randomUUID();
    
    @Convert(converter = AesEncryptor.class)
    private String name;

    @ManyToOne  
    @JoinColumn(name = "tracker_id", referencedColumnName = "uuid") 
    @JsonIgnore
    private MedicationTracker medicationTracker; 
}