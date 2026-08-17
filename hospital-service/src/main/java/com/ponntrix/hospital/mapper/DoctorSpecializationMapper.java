package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.dto.DoctorSpecializationDto;
import com.ponntrix.hospital.entity.DoctorSpecialization;


public class DoctorSpecializationMapper {

    public static DoctorSpecialization toEntity(DoctorSpecializationDto dto) {

        DoctorSpecialization doctorSpecialization = new DoctorSpecialization();

        doctorSpecialization.setIsPrimary(dto.getIsPrimary());
        doctorSpecialization.setCreatedBy(dto.getCreatedBy());
        doctorSpecialization.setUpdatedBy(dto.getUpdatedBy());

        return doctorSpecialization;
    }

    public static DoctorSpecializationDto toDto(DoctorSpecialization entity) {

        DoctorSpecializationDto dto = new DoctorSpecializationDto();

        dto.setDoctorSpecializationsUUID(entity.getDoctorSpecializationsUUID());
        if (entity.getDoctor() != null) {
            dto.setDoctorId(entity.getDoctor().getDoctorUUID());
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
