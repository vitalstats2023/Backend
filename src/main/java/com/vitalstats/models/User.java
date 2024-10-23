package com.vitalstats.models;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vitalstats.utils.AesEncryptor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID uuid = UUID.randomUUID();  // No encryption on UUID

    @Column(unique = true)
    @Convert(converter = AesEncryptor.class)
    private String vsid;

    @Convert(converter = AesEncryptor.class)
    private String aadhaarNumber;

    @Convert(converter = AesEncryptor.class)
    private String email;

    @Convert(converter = AesEncryptor.class)
    private String phoneNumber;

    @Convert(converter = AesEncryptor.class)
    private String password;
 

    @ManyToOne
    private Family family;
}
