package com.ponntrix.hospital.service.locationService;


import com.ponntrix.hospital.dto.request.AddressRequestDto;
import com.ponntrix.hospital.dto.response.AddressResponseDto;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.locations.*;
import com.ponntrix.hospital.mapper.AddressMapper;
import com.ponntrix.hospital.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final AreaRepository areaRepository;

    private static final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    public AddressResponseDto createAddress(AddressRequestDto dto) {

        if (addressRepository.existsByEntityTypeAndEntityId(
                dto.getEntityType(),
                dto.getEntityId())) {

            throw new RuntimeException("Address already exists for this entity.");
        }

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found."));

        State state = stateRepository.findById(dto.getStateId())
                .orElseThrow(() -> new RuntimeException("State not found."));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found."));

        Area area = areaRepository.findById(dto.getAreaId())
                .orElseThrow(() -> new RuntimeException("Area not found."));

        Address address = AddressMapper.toEntity(dto);

        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        address.setArea(area);

        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            Point point = geometryFactory.createPoint(
                    new Coordinate(dto.getLongitude(), dto.getLatitude()));

            point.setSRID(4326);
            address.setLocation(point);
        }
        Address savedAddress = addressRepository.save(address);

        return AddressMapper.toDto(savedAddress);
    }


    @Override
    public List<AddressResponseDto> getAllAddresses() {

        return addressRepository.findAll()
                .stream()
                .map(AddressMapper::toDto)
                .toList();
    }


    @Override
    public AddressResponseDto getAddressById(UUID addressId) {
        Address address = addressRepository.findByAddressUUID(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));
        return AddressMapper.toDto(address);
    }


    @Override
    public AddressResponseDto getAddressByEntity(
            EntityType entityType,
            UUID entityId) {

        Address address = addressRepository
                .findByEntityTypeAndEntityId(entityType, entityId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found."));

        return AddressMapper.toDto(address);
    }


    @Override
    public AddressResponseDto updateAddress(
            UUID addressId,
            AddressRequestDto dto) {

        Address address = addressRepository.findByAddressUUID(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found."));

        State state = stateRepository.findById(dto.getStateId())
                .orElseThrow(() -> new RuntimeException("State not found."));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found."));

        Area area = areaRepository.findById(dto.getAreaId())
                .orElseThrow(() -> new RuntimeException("Area not found."));

        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        address.setArea(area);

        address.setLandmark(dto.getLandmark());
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setPincode(dto.getPincode());
        address.setIsPrimary(dto.getIsPrimary());
        address.setIsActive(dto.getIsActive());
        address.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getLatitude() != null &&
                dto.getLongitude() != null) {

            Point point = geometryFactory.createPoint(
                    new Coordinate(
                            dto.getLongitude(),
                            dto.getLatitude()));

            point.setSRID(4326);
            address.setLocation(point);
        }
        Address updatedAddress = addressRepository.save(address);

        return AddressMapper.toDto(updatedAddress);
    }

    @Override
    @Transactional
    public AddressResponseDto updateAddressByEntity(
            EntityType entityType,
            UUID entityId,
            AddressRequestDto dto) {

        Address address =
                addressRepository
                        .findByEntityTypeAndEntityId(
                                entityType,
                                entityId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Address not found."
                                )
                        );
        Country country =
                countryRepository.findById(dto.getCountryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Country not found."
                                )
                        );
        State state =
                stateRepository.findById(dto.getStateId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "State not found."
                                )
                        );
        City city =
                cityRepository.findById(dto.getCityId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "City not found."
                                )
                        );
        Area area =
                areaRepository.findById(dto.getAreaId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Area not found."
                                )
                        );


        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        address.setArea(area);

        address.setLandmark(dto.getLandmark());
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setPincode(dto.getPincode());
        address.setIsPrimary(dto.getIsPrimary());
        address.setIsActive(dto.getIsActive());
        address.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getLatitude() != null &&
                dto.getLongitude() != null) {

            GeometryFactory geometryFactory =
                    new GeometryFactory(
                            new PrecisionModel(),
                            4326
                    );

            Point point =
                    geometryFactory.createPoint(
                            new Coordinate(
                                    dto.getLongitude(),
                                    dto.getLatitude()
                            )
                    );

            address.setLocation(point);
        }

        Address updatedAddress = addressRepository.save(address);
        return AddressMapper.toDto(updatedAddress);
    }

    @Override
    public void deleteAddress(UUID addressId) {
        Address address = addressRepository.findByAddressUUID(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));
        addressRepository.delete(address);
    }

}
