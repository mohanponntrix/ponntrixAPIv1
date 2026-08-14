package com.ponntrix.hospital.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecializationRequestDto {

    private String specializationName;

    private Integer qualificationId;

    private Integer createdBy;

    private Integer updatedBy;

}
