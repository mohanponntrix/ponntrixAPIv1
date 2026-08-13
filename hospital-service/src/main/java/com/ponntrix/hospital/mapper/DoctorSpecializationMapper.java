package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.dto.requestDto.DoctorSpecializationRequestDto;
import com.ponntrix.hospital.dto.responseDto.DoctorSpecializationResponseDto;
import com.ponntrix.hospital.entity.DoctorSpecialization;


public class DoctorSpecializationMapper {

    public static DoctorSpecialization toEntity(DoctorSpecializationRequestDto dto) {

        DoctorSpecialization doctorSpecialization = new DoctorSpecialization();

        doctorSpecialization.setIsPrimary(dto.getIsPrimary());
        doctorSpecialization.setCreatedBy(dto.getCreatedBy());
        doctorSpecialization.setUpdatedBy(dto.getUpdatedBy());

        return doctorSpecialization;
    }

    public static DoctorSpecializationResponseDto toDto(DoctorSpecialization entity) {

        DoctorSpecializationResponseDto dto = new DoctorSpecializationResponseDto();

        dto.setDoctorSpecializationsId(entity.getDoctorSpecializationsId());
        dto.setDoctorSpecializationsUUID(entity.getDoctorSpecializationsUUID());

        if (entity.getDoctor() != null) {
            dto.setDoctorId(entity.getDoctor().getDoctorId());
            dto.setDoctorName(entity.getDoctor().getFirstName());
        }
        if (entity.getSpecialization() != null) {
            dto.setSpecializationId(entity.getSpecialization().getSpecializationId());
            dto.setSpecializationName(entity.getSpecialization().getSpecializationName());
        }
        dto.setIsPrimary(entity.getIsPrimary());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        return dto;
    }

}
