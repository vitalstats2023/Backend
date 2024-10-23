package com.vitalstats.models;

import java.util.UUID;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vitalstats.utils.AesEncryptor;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class HealthFiles {

    @Id
    @Column(columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID uuid = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "folder_id", referencedColumnName = "uuid", nullable = false)
    private HealthFolder healthFolder;

    @Convert(converter = AesEncryptor.class)
    private String type;

    @Lob
    @Column(name = "file", nullable = false, columnDefinition = "LONGTEXT")
    @Convert(converter = AesEncryptor.class)
    private String file;

    @Column(nullable = false)
    @Convert(converter = AesEncryptor.class)
    private String fileType;

    @Convert(converter = AesEncryptor.class)
    private String summary;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
