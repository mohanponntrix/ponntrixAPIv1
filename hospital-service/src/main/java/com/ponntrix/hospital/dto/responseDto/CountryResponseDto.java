package com.ponntrix.hospital.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CountryResponseDto {

    private Integer countryId;

    private String countryName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
