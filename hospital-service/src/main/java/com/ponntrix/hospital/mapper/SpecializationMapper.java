package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.request.SpecializationRequestDto;
import com.ponntrix.hospital.dto.response.SpecializationResponseDto;
import com.ponntrix.hospital.entity.Qualification;
import com.ponntrix.hospital.entity.Specialization;

public class SpecializationMapper {

    public static Specialization toEntity(SpecializationRequestDto dto, Qualification qualification) {

        Specialization specialization = new Specialization();
        specialization.setSpecializationName(dto.getSpecializationName());
        specialization.setQualification(qualification);
        specialization.setCreatedBy(dto.getCreatedBy());
        specialization.setUpdatedBy(dto.getUpdatedBy());
        return specialization;
    }


    public static SpecializationResponseDto toDto(Specialization specialization) {

        SpecializationResponseDto dto = new SpecializationResponseDto();

        dto.setSpecializationId(specialization.getSpecializationId());
        dto.setSpecializationName(specialization.getSpecializationName());
        if (specialization.getQualification() != null) {
            dto.setQualificationId(specialization.getQualification().getQualificationId());
            dto.setQualificationName(specialization.getQualification().getQualificationName());
        }
        dto.setCreatedAt(specialization.getCreatedAt());
        dto.setUpdatedAt(specialization.getUpdatedAt());
        dto.setCreatedBy(specialization.getCreatedBy());
        dto.setUpdatedBy(specialization.getUpdatedBy());
        return dto;
    }
}
