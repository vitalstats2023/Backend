package com.vitalstats.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Profile {

    @Id
    @Column(columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID uuid = UUID.randomUUID();

    private String name;
    private String dob; 
    private String address;
    private String BloodGrp;
    private double height;
    private double weight;
    private String profession;
    private List<String> chronicdieases = new  ArrayList<>();
    private double BMI;
    private String lifestyle;
    private double routinemovement;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uuid")
    private User user;

}
