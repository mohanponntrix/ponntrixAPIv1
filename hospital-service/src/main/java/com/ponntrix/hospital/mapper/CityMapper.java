package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.entity.locations.City;
import com.ponntrix.hospital.dto.request.CityRequestDto;
import com.ponntrix.hospital.dto.response.CityResponseDto;

public class CityMapper {

    public static City toEntity(CityRequestDto dto) {

        City city = new City();

        city.setCityName(dto.getCityName());
        city.setCreatedBy(dto.getCreatedBy());
        city.setUpdatedBy(dto.getUpdatedBy());

        return city;
    }

    public static CityResponseDto toDto(City city) {

        CityResponseDto dto = new CityResponseDto();

        dto.setCityId(city.getCityId());
        dto.setCityName(city.getCityName());

        if (city.getState() != null) {

            dto.setStateId(city.getState().getStateId());
            dto.setStateName(city.getState().getStateName());

        }

        dto.setCreatedAt(city.getCreatedAt());
        dto.setUpdatedAt(city.getUpdatedAt());
        dto.setCreatedBy(city.getCreatedBy());
        dto.setUpdatedBy(city.getUpdatedBy());

        return dto;
    }

}
