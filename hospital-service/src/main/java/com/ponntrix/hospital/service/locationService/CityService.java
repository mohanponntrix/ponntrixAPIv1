package com.ponntrix.hospital.service.locationService;

import com.ponntrix.hospital.dto.request.CityRequestDto;
import com.ponntrix.hospital.dto.response.CityResponseDto;

import java.util.List;

public interface CityService {

    CityResponseDto createCity(CityRequestDto dto);

    List<CityResponseDto> getAllCities();

    CityResponseDto getCityById(Integer cityId);

    CityResponseDto updateCity(Integer cityId, CityRequestDto dto);

    void deleteCity(Integer cityId);

}
