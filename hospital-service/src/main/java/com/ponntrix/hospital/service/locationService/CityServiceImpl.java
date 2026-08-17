package com.ponntrix.hospital.service.locationService;

import com.ponntrix.hospital.entity.locations.City;
import com.ponntrix.hospital.entity.locations.State;
import com.ponntrix.hospital.mapper.CityMapper;
import com.ponntrix.hospital.repository.CityRepository;
import com.ponntrix.hospital.repository.StateRepository;
import com.ponntrix.hospital.dto.request.CityRequestDto;
import com.ponntrix.hospital.dto.response.CityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Override
    public CityResponseDto createCity(CityRequestDto dto) {

        if (cityRepository.existsByCityNameIgnoreCase(dto.getCityName())) {
            throw new RuntimeException("City already exists.");
        }

        State state = stateRepository.findById(dto.getStateId())
                .orElseThrow(() ->
                        new RuntimeException("State not found."));

        City city = CityMapper.toEntity(dto);
        city.setState(state);
        City savedCity = cityRepository.save(city);

        return CityMapper.toDto(savedCity);
    }


    @Override
    public List<CityResponseDto> getAllCities() {

        return cityRepository.findAll()
                .stream()
                .map(CityMapper::toDto)
                .toList();
    }


    @Override
    public CityResponseDto getCityById(Integer cityId) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() ->
                        new RuntimeException("City not found."));

        return CityMapper.toDto(city);
    }


    @Override
    public CityResponseDto updateCity(
            Integer cityId,
            CityRequestDto dto) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() ->
                        new RuntimeException("City not found."));

        State state = stateRepository.findById(dto.getStateId())
                .orElseThrow(() ->
                        new RuntimeException("State not found."));

        city.setCityName(dto.getCityName());
        city.setState(state);
        city.setUpdatedBy(dto.getUpdatedBy());

        City updatedCity = cityRepository.save(city);

        return CityMapper.toDto(updatedCity);
    }


    @Override
    public void deleteCity(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found."));
        cityRepository.delete(city);
    }

}
