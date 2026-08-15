package com.ponntrix.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DoctorSpecializationDto {
    private UUID doctorSpecializationsUUID;
    private UUID doctorId;
    private String doctorName;
    private Integer specializationId;
    private String specializationName;
    private Boolean isPrimary;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
}
