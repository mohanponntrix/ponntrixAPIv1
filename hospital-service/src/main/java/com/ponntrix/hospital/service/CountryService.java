package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.request.CountryRequestDto;
import com.ponntrix.hospital.dto.response.CountryResponseDto;

import java.util.List;

public interface CountryService {

    CountryResponseDto createCountry(CountryRequestDto dto);

    List<CountryResponseDto> getAllCountries();

    CountryResponseDto getCountryById(Integer countryId);

    CountryResponseDto updateCountry(
            Integer countryId,
            CountryRequestDto dto);

    void deleteCountry(Integer countryId);

}
