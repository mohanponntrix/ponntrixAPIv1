package com.ponntrix.hospital.dto;

import com.ponntrix.hospital.entity.EntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OperatingHoursDto {
    private UUID operatingHoursUUID;
    private EntityType entityType;
    private UUID entityId;
    private Integer dayOfWeekId;
    private String dayName;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean isClosed;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
}
