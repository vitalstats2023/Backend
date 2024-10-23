package com.vitalstats.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import lombok.Data;

@Entity
@Data
public class Family {

    @Id
    @Column(columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID uuid = UUID.randomUUID();

    @OneToOne
    private User user;

    private String relationship;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> members = new ArrayList<>();
}
