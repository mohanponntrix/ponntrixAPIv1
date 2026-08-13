package com.ponntrix.hospital.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateRequestDto {

    private String stateName;

    private Integer countryId;

    private Integer createdBy;

    private Integer updatedBy;

}