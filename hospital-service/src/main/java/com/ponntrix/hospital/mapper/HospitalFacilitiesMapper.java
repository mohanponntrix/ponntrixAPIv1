package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.dto.HospitalFacilitiesDto;
import com.ponntrix.hospital.entity.HospitalFacilities;

public class HospitalFacilitiesMapper {

    public static HospitalFacilities toEntity(HospitalFacilitiesDto dto) {

        HospitalFacilities facility = new HospitalFacilities();

        facility.setFacilityName(dto.getFacilityName());
        facility.setDescription(dto.getDescription());
        facility.setIsActive(dto.getIsActive());
        facility.setCreatedBy(dto.getCreatedBy());
        facility.setUpdatedBy(dto.getUpdatedBy());
        return facility;
    }


    public static HospitalFacilitiesDto toDto(HospitalFacilities facility) {

        HospitalFacilitiesDto dto = new HospitalFacilitiesDto();

        dto.setHospitalFacilitiesUUID(facility.getHospitalFacilitiesUUID());
        if (facility.getHospital() != null) {
            dto.setHospitalUUID(
                    facility.getHospital()
                            .getHospitalUUID());
        }
        dto.setFacilityName(facility.getFacilityName());
        dto.setDescription(facility.getDescription());
        dto.setIconUrl(facility.getIconUrl());
        dto.setIsActive(facility.getIsActive());
        dto.setCreatedAt(facility.getCreatedAt());
        dto.setUpdatedAt(facility.getUpdatedAt());
        dto.setCreatedBy(facility.getCreatedBy());
        dto.setUpdatedBy(facility.getUpdatedBy());

        return dto;
    }
}
