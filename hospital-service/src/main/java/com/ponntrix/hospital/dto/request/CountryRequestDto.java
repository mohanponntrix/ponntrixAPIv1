package com.ponntrix.hospital.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryRequestDto {

    private String countryName;

    private Integer createdBy;

    private Integer updatedBy;

}
