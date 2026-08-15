package com.ponntrix.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HospitalFacilitiesDto {
    private UUID hospitalFacilitiesUUID;
    private UUID hospitalUUID;
    private String facilityName;
    private String description;
    private String iconUrl;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
}
