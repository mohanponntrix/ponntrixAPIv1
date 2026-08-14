package com.ponntrix.hospital.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QualificationRequestDto {
    private String qualificationName;

    private Integer createdBy;

    private Integer updatedBy;
}
