package com.ponntrix.hospital.dto.response;

import com.ponntrix.hospital.entity.EntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DocumentsResponseDto {
    private Integer documentsId;

    private UUID documentsUUID;

    private EntityType entityType;

    private Integer entityId;

    private String documentName;

    private String documentUrlLink;

    private Boolean isVerified;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private String issuedBy;

    private OffsetDateTime issuedDate;

    private OffsetDateTime expiryDate;

    private Integer createdBy;

    private Integer updatedBy;

}
