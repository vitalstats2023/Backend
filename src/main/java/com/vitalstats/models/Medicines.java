package com.vitalstats.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitalstats.utils.AesEncryptor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Medicines {
    
    @Id
    @Column(columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID uuid = UUID.randomUUID();
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "prescription_id", referencedColumnName = "uuid") 
    @JsonIgnore
    private Prescription prescription;
    
    @Convert(converter = AesEncryptor.class)
    private String name;
    @Convert(converter = AesEncryptor.class)
    private String startDate;
    @Convert(converter = AesEncryptor.class)
    private String endDate;
    @Convert(converter = AesEncryptor.class)
    private int frequency;

    @Convert(converter = AesEncryptor.class)
    private List<String> time = new ArrayList<>();
}
