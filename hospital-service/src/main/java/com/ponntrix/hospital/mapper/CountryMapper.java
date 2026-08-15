package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.entity.locations.Country;
import com.ponntrix.hospital.dto.request.CountryRequestDto;
import com.ponntrix.hospital.dto.response.CountryResponseDto;

public class CountryMapper {

    public static Country toEntity(CountryRequestDto dto) {

        Country country = new Country();

        country.setCountryName(dto.getCountryName());
        country.setCreatedBy(dto.getCreatedBy());
        country.setUpdatedBy(dto.getUpdatedBy());

        return country;
    }

    public static CountryResponseDto toDto(Country country) {

        CountryResponseDto dto = new CountryResponseDto();

        dto.setCountryId(country.getCountryId());
        dto.setCountryName(country.getCountryName());
        dto.setCreatedAt(country.getCreatedAt());
        dto.setUpdatedAt(country.getUpdatedAt());
        dto.setCreatedBy(country.getCreatedBy());
        dto.setUpdatedBy(country.getUpdatedBy());

        return dto;
    }

}
