package com.ponntrix.hospital.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CityRequestDto {

    private String cityName;

    private Integer stateId;

    private Integer createdBy;

    private Integer updatedBy;

}
