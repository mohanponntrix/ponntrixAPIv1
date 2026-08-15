package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.request.*;
import com.ponntrix.hospital.dto.response.*;
import com.ponntrix.hospital.entity.locations.Area;
import com.ponntrix.hospital.entity.locations.City;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.locations.State;
import com.ponntrix.hospital.repository.AreaRepository;
import com.ponntrix.hospital.repository.CityRepository;
import com.ponntrix.hospital.repository.StateRepository;
import com.ponntrix.hospital.service.locationService.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {

    private final AddressService addressService;
    private final CountryService countryService;
    private final StateService stateService;
    private final StateRepository stateRepository;
    private final CityService cityService;
    private final CityRepository cityRepository;
    private final AreaService areaService;
    private final AreaRepository areaRepository;

    @PostMapping("/addresses/create")
    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto dto) {
        return ResponseEntity.ok(addressService.createAddress(dto));
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }


    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable UUID addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }


    @GetMapping("/addresses/entity")
    public ResponseEntity<AddressResponseDto> getAddressByEntity(
            @RequestParam EntityType entityType,
            @RequestParam UUID entityId) {
        return ResponseEntity.ok(addressService.getAddressByEntity(entityType, entityId));
    }


    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable UUID addressId,
            @RequestBody AddressRequestDto dto) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, dto));
    }


    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully.");
    }

    //Countries Controllers

    @PostMapping("/countries/create")
    public ResponseEntity<CountryResponseDto> createCountry(
            @RequestBody CountryRequestDto dto) {
        return ResponseEntity.ok(
                countryService.createCountry(dto));
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryResponseDto>> getAllCountries() {
        return ResponseEntity.ok(
                countryService.getAllCountries());
    }

    //States Controllers

    @PostMapping("/states/create")
    public ResponseEntity<StateResponseDto> createState(@RequestBody StateRequestDto dto) {
        return ResponseEntity.ok(stateService.createState(dto));
    }

    @GetMapping("/states")
    public ResponseEntity<List<StateResponseDto>> getAllStates() {
        return ResponseEntity.ok(stateService.getAllStates());
    }

    @GetMapping("/states/country/{countryId}")
    public ResponseEntity<List<State>> getStatesByCountry(@PathVariable Integer countryId) {
        return ResponseEntity.ok(stateRepository.findByCountry_CountryId(countryId)
        );
    }

    //Cities Controllers

    @PostMapping("/cities/create")
    public ResponseEntity<CityResponseDto> createCity(
            @RequestBody CityRequestDto dto) {
        return ResponseEntity.ok(cityService.createCity(dto));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityResponseDto>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/cities/state/{stateId}")
    public ResponseEntity<List<City>> getCitiesByState(@PathVariable Integer stateId) {
        return ResponseEntity.ok(cityRepository.findByStateStateId(stateId));
    }

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<CityResponseDto> getCityById(@PathVariable Integer cityId) {
        return ResponseEntity.ok(cityService.getCityById(cityId));
    }

    //Areas Controllers

    @PostMapping("/areas/create")
    public ResponseEntity<AreaResponseDto> createArea(@RequestBody AreaRequestDto dto) {
        return ResponseEntity.ok(areaService.createArea(dto));
    }

    @GetMapping("/areas")
    public ResponseEntity<List<AreaResponseDto>> getAllAreas() {
        return ResponseEntity.ok(areaService.getAllAreas());
    }

    @GetMapping("/areas/city/{cityId}")
    public ResponseEntity<List<Area>> getAreaByCity(@PathVariable Integer cityId){
        return ResponseEntity.ok(areaRepository.findByCityCityId(cityId));
    }

}
