package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.requestDto.CityRequestDto;
import com.ponntrix.hospital.dto.responseDto.CityResponseDto;

import java.util.List;

public interface CityService {

    CityResponseDto createCity(CityRequestDto dto);

    List<CityResponseDto> getAllCities();

    CityResponseDto getCityById(Integer cityId);

    CityResponseDto updateCity(Integer cityId, CityRequestDto dto);

    void deleteCity(Integer cityId);

}
