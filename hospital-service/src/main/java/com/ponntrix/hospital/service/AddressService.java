package com.ponntrix.hospital.service;


import com.ponntrix.hospital.dto.requestDto.AddressRequestDto;
import com.ponntrix.hospital.dto.responseDto.AddressResponseDto;
import com.ponntrix.hospital.entity.EntityType;

import java.util.List;

public interface AddressService {

    AddressResponseDto createAddress(AddressRequestDto dto);

    List<AddressResponseDto> getAllAddresses();

    AddressResponseDto getAddressById(Integer addressId);

    AddressResponseDto getAddressByEntity(EntityType entityType, Integer entityId);

    AddressResponseDto updateAddress(Integer addressId, AddressRequestDto dto);

    AddressResponseDto updateAddressByEntity(EntityType entityType, Integer entityId, AddressRequestDto dto
    );

    void deleteAddress(Integer addressId);

}