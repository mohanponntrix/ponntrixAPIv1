package com.ponntrix.hospital.dto;

import com.ponntrix.hospital.entity.EntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DocumentsDto {
    private UUID documentsUUID;
    private EntityType entityType;
    private UUID entityId;
    private String documentName;
    private String documentUrlLink;
    private Boolean isVerified;
    private OffsetDateTime issuedDate;
    private OffsetDateTime expiryDate;
    private String issuedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
}
