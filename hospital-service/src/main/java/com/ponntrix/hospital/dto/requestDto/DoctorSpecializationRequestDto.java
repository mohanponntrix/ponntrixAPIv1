package com.ponntrix.hospital.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorSpecializationRequestDto {

    private Integer doctorId;

    private Integer specializationId;

    private Boolean isPrimary;

    private Integer createdBy;

    private Integer updatedBy;

}
