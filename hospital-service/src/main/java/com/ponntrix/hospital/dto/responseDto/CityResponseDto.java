package com.ponntrix.hospital.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CityResponseDto {

    private Integer cityId;

    private String cityName;

    private Integer stateId;

    private String stateName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
