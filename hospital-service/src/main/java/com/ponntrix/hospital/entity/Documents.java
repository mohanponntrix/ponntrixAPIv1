package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "documents",
        schema = "ponntrix_hospital",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "documents_unique_key",
                        columnNames = {
                                "entity_type",
                                "entity_id",
                                "document_name"
                        })
        })
@Getter
@Setter
@NoArgsConstructor
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documents_id")
    private Integer documentsId;

    @Column(name = "documents_uuid", nullable = false, unique = true)
    private UUID documentsUUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    @Column(name = "documents_name", nullable = false)
    private String documentName;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "document_type", nullable = false)
//    private DocumentType documentType;

    @Column(name = "document_url_link", nullable = false)
    private String documentUrlLink;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "issued_date")
    private OffsetDateTime issuedDate;

    @Column(name = "expiry_date")
    private OffsetDateTime expiryDate;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @PrePersist
    public void prePersist() {

        documentsUUID = UUID.randomUUID();

        createdAt = OffsetDateTime.now();

        updatedAt = OffsetDateTime.now();

        if (isVerified == null) {
            isVerified = false;
        }
    }

    @PreUpdate
    public void preUpdate() {

        updatedAt = OffsetDateTime.now();

    }

}