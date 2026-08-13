package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.requestDto.AddressRequestDto;
import com.ponntrix.hospital.dto.responseDto.AddressResponseDto;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<AddressResponseDto> createAddress(
            @RequestBody AddressRequestDto dto) {

        return ResponseEntity.ok(addressService.createAddress(dto));
    }


    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }


    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById(
            @PathVariable Integer addressId) {

        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }


    @GetMapping("/entity")
    public ResponseEntity<AddressResponseDto> getAddressByEntity(
            @RequestParam EntityType entityType,
            @RequestParam Integer entityId) {

        return ResponseEntity.ok(addressService.getAddressByEntity(entityType, entityId));
    }


    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Integer addressId,
            @RequestBody AddressRequestDto dto) {

        return ResponseEntity.ok(addressService.updateAddress(addressId, dto));
    }


    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Integer addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully.");
    }

}
