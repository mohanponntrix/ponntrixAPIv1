package com.ponntrix.hospital.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SpecializationResponseDto {

    private Integer specializationId;

    private String specializationName;

    private Integer qualificationId;

    private String qualificationName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}