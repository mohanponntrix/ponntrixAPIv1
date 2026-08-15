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
public class SocialLinksDto {
    private UUID socialLinksUUID;
    private EntityType entityType;
    private UUID entityId;
    private String socialMediaName;
    private String socialMediaLink;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
}
