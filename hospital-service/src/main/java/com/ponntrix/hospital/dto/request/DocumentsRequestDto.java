package com.ponntrix.hospital.dto.request;

import com.ponntrix.hospital.entity.EntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DocumentsRequestDto {
    private EntityType entityType;

    private Integer entityId;

    private String documentName;

    private Integer documentsId;

    private Integer createdBy;

    private Integer updatedBy;

    private String issuedBy;

    private OffsetDateTime issuedDate;

    private OffsetDateTime expiryDate;
}
