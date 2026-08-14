package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.request.HospitalRequestDto;
import com.ponntrix.hospital.dto.response.HospitalResponseDto;
import com.ponntrix.hospital.entity.Hospital;

public class HospitalMapper {

    public static Hospital toEntity(HospitalRequestDto dto) {

        Hospital hospital = new Hospital();

        hospital.setHospitalType(dto.getHospitalType());
        hospital.setHospitalName(dto.getHospitalName());
        hospital.setRegistrationNumber(dto.getRegistrationNumber());
        hospital.setGstNumber(dto.getGstNumber());
        hospital.setEstablishedYear(dto.getEstablishedYear());
        hospital.setHospitalPhone(dto.getHospitalPhone());
        hospital.setHospitalEmail(dto.getHospitalEmail());
        hospital.setWebsite(dto.getWebsite());
        hospital.setEmergencyAvailable(dto.getEmergencyAvailable());
        hospital.setOverView(dto.getOverView());
        hospital.setOrganizationId(dto.getOrganizationId());
        hospital.setCreatedBy(dto.getCreatedBy());
        hospital.setUpdatedBy(dto.getUpdatedBy());


        return hospital;
    }

    public static HospitalResponseDto toDto(Hospital hospital) {

        HospitalResponseDto dto = new HospitalResponseDto();

        dto.setHospitalId(hospital.getHospitalId());
        dto.setHospitalUUID(hospital.getHospitalUUID());
        dto.setHospitalType(hospital.getHospitalType());
        dto.setHospitalName(hospital.getHospitalName());
        dto.setRegistrationNumber(hospital.getRegistrationNumber());
        dto.setGstNumber(hospital.getGstNumber());
        dto.setEstablishedYear(hospital.getEstablishedYear());
        dto.setHospitalPhone(hospital.getHospitalPhone());
        dto.setHospitalEmail(hospital.getHospitalEmail());
        dto.setWebsite(hospital.getWebsite());
        dto.setLogoUrl(hospital.getLogoUrl());
        dto.setCoverImageUrl(hospital.getCoverImageUrl());
        dto.setEmergencyAvailable(hospital.getEmergencyAvailable());
        dto.setOverView(hospital.getOverView());
        dto.setOrganizationId(hospital.getOrganizationId());
        dto.setCreatedAt(hospital.getCreatedAt());
        dto.setUpdatedAt(hospital.getUpdatedAt());
        dto.setCreatedBy(hospital.getCreatedBy());
        dto.setUpdatedBy(hospital.getUpdatedBy());

        if (hospital.getOnboardingStatus() != null) {

            dto.setOnboardingStatusId(
                    hospital.getOnboardingStatus().getStatusId());

            dto.setOnboardingStatusName(
                    hospital.getOnboardingStatus().getStatusName());
        }

        return dto;
    }

}
