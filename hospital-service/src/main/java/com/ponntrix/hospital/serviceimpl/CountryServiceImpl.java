package com.ponntrix.hospital.serviceimpl;

import com.ponntrix.hospital.entity.Country;
import com.ponntrix.hospital.mapper.CountryMapper;
import com.ponntrix.hospital.repository.CountryRepository;
import com.ponntrix.hospital.dto.request.CountryRequestDto;
import com.ponntrix.hospital.dto.response.CountryResponseDto;
import com.ponntrix.hospital.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public CountryResponseDto createCountry(CountryRequestDto dto) {

        if (countryRepository.existsByCountryNameIgnoreCase(dto.getCountryName())) {
            throw new RuntimeException("Country already exists.");
        }

        Country country = CountryMapper.toEntity(dto);

        Country savedCountry = countryRepository.save(country);

        return CountryMapper.toDto(savedCountry);
    }

    @Override
    public List<CountryResponseDto> getAllCountries() {

        return countryRepository.findAll()
                .stream()
                .map(CountryMapper::toDto)
                .toList();
    }

    @Override
    public CountryResponseDto getCountryById(Integer countryId) {

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() ->
                        new RuntimeException("Country not found."));

        return CountryMapper.toDto(country);
    }

    @Override
    public CountryResponseDto updateCountry(
            Integer countryId,
            CountryRequestDto dto) {

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() ->
                        new RuntimeException("Country not found."));

        country.setCountryName(dto.getCountryName());
        country.setUpdatedBy(dto.getUpdatedBy());

        Country updatedCountry = countryRepository.save(country);

        return CountryMapper.toDto(updatedCountry);
    }

    @Override
    public void deleteCountry(Integer countryId) {

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() ->
                        new RuntimeException("Country not found."));

        countryRepository.delete(country);
    }

}
