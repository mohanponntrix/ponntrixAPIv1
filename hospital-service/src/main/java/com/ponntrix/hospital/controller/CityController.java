package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.entity.City;
import com.ponntrix.hospital.repository.CityRepository;
import com.ponntrix.hospital.dto.requestDto.CityRequestDto;
import com.ponntrix.hospital.dto.responseDto.CityResponseDto;
import com.ponntrix.hospital.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final CityRepository cityRepository;

    @PostMapping("/create")
    public ResponseEntity<CityResponseDto> createCity(
            @RequestBody CityRequestDto dto) {
        return ResponseEntity.ok(cityService.createCity(dto));
    }

    @GetMapping
    public ResponseEntity<List<CityResponseDto>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<City>> getCitiesByState(
            @PathVariable Integer stateId) {

        return ResponseEntity.ok(
                cityRepository.findByStateStateId(stateId)
        );
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<CityResponseDto> getCityById(@PathVariable Integer cityId) {
        return ResponseEntity.ok(cityService.getCityById(cityId));
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<CityResponseDto> updateCity(
            @PathVariable Integer cityId,
            @RequestBody CityRequestDto dto) {
        return ResponseEntity.ok(cityService.updateCity(cityId, dto));
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> deleteCity(@PathVariable Integer cityId) {
        cityService.deleteCity(cityId);
        return ResponseEntity.ok("City deleted successfully.");
    }
}
