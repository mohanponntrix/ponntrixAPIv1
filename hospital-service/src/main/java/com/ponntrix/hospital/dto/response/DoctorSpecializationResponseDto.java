package com.ponntrix.hospital.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DoctorSpecializationResponseDto {

    private Integer doctorSpecializationsId;

    private UUID doctorSpecializationsUUID;

    private Integer doctorId;

    private String doctorName;

    private Integer specializationId;

    private String specializationName;

    private Boolean isPrimary;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
