package com.ponntrix.hospital.service.locationService;


import com.ponntrix.hospital.dto.request.AddressRequestDto;
import com.ponntrix.hospital.dto.response.AddressResponseDto;
import com.ponntrix.hospital.entity.EntityType;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressResponseDto createAddress(AddressRequestDto dto);

    List<AddressResponseDto> getAllAddresses();

    AddressResponseDto getAddressById(UUID addressId);

    AddressResponseDto getAddressByEntity(EntityType entityType, UUID entityId);

    AddressResponseDto updateAddress(UUID addressId, AddressRequestDto dto);

    AddressResponseDto updateAddressByEntity(EntityType entityType, UUID entityId, AddressRequestDto dto
    );

    void deleteAddress(UUID addressId);

}
