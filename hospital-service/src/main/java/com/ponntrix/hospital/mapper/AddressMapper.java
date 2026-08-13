package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.entity.Address;
import com.ponntrix.hospital.dto.requestDto.AddressRequestDto;
import com.ponntrix.hospital.dto.responseDto.AddressResponseDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class AddressMapper {

    private static final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);

    public static Address toEntity(AddressRequestDto dto) {

        Address address = new Address();

        address.setEntityType(dto.getEntityType());
        address.setEntityId(dto.getEntityId());

        address.setLandmark(dto.getLandmark());
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setPincode(dto.getPincode());

        address.setIsPrimary(dto.getIsPrimary());
        address.setIsActive(dto.getIsActive());

        address.setCreatedBy(dto.getCreatedBy());
        address.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            Point point = geometryFactory.createPoint(
                    new Coordinate(
                            dto.getLongitude(),   // X
                            dto.getLatitude()     // Y
                    )
            );

            point.setSRID(4326);

            address.setLocation(point);
        }

        return address;
    }

    public static AddressResponseDto toDto(Address address) {

        AddressResponseDto dto = new AddressResponseDto();

        dto.setAddressId(address.getAddressId());
        dto.setAddressUUID(address.getAddressUUID());

        dto.setEntityType(address.getEntityType());
        dto.setEntityId(address.getEntityId());

        if (address.getCountry() != null) {
            dto.setCountryId(address.getCountry().getCountryId());
            dto.setCountryName(address.getCountry().getCountryName());
        }

        if (address.getState() != null) {
            dto.setStateId(address.getState().getStateId());
            dto.setStateName(address.getState().getStateName());
        }

        if (address.getCity() != null) {
            dto.setCityId(address.getCity().getCityId());
            dto.setCityName(address.getCity().getCityName());
        }

        if (address.getArea() != null) {
            dto.setAreaId(address.getArea().getAreaId());
            dto.setAreaName(address.getArea().getAreaName());
        }

        if (address.getLocation() != null) {
            dto.setLatitude(address.getLocation().getY());
            dto.setLongitude(address.getLocation().getX());
        }

        dto.setLandmark(address.getLandmark());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setPincode(address.getPincode());

        dto.setIsPrimary(address.getIsPrimary());
        dto.setIsActive(address.getIsActive());

        dto.setCreatedAt(address.getCreatedAt());
        dto.setUpdatedAt(address.getUpdatedAt());

        dto.setCreatedBy(address.getCreatedBy());
        dto.setUpdatedBy(address.getUpdatedBy());

        return dto;
    }

}
