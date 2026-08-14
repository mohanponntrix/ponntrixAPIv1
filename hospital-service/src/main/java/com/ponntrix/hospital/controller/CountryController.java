package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.request.CountryRequestDto;
import com.ponntrix.hospital.dto.response.CountryResponseDto;
import com.ponntrix.hospital.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController{

    private final CountryService countryService;

    @PostMapping("/create")
    public ResponseEntity<CountryResponseDto> createCountry(
            @RequestBody CountryRequestDto dto) {
        return ResponseEntity.ok(
                countryService.createCountry(dto));
    }

    @GetMapping
    public ResponseEntity<List<CountryResponseDto>> getAllCountries() {
        return ResponseEntity.ok(
                countryService.getAllCountries());
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryResponseDto> getCountryById(
            @PathVariable Integer countryId) {
        return ResponseEntity.ok(
                countryService.getCountryById(countryId));
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<CountryResponseDto> updateCountry(
            @PathVariable Integer countryId,
            @RequestBody CountryRequestDto dto) {
        return ResponseEntity.ok(
                countryService.updateCountry(countryId, dto));
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<String> deleteCountry(
            @PathVariable Integer countryId) {
        countryService.deleteCountry(countryId);
        return ResponseEntity.ok("Country deleted successfully.");
    }

}
