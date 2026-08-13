package com.ponntrix.hospital.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QualificationResponseDto {
    private Integer qualificationId;

    private String qualificationName;

    private Integer doctorCount;

    private Integer specializationCount;

    private OffsetDateTime createdAt;

    private Integer createdBy;

    private OffsetDateTime updatedAt;

    private Integer updatedBy;
}
