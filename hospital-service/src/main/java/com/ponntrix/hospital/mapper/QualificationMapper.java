package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.requestDto.QualificationRequestDto;
import com.ponntrix.hospital.dto.responseDto.QualificationResponseDto;
import com.ponntrix.hospital.entity.Qualification;

public class QualificationMapper {
    public static Qualification toEntity(QualificationRequestDto dto) {

        Qualification qualification = new Qualification();

        qualification.setQualificationName(dto.getQualificationName());
        qualification.setCreatedBy(dto.getCreatedBy());
        qualification.setUpdatedBy(dto.getUpdatedBy());

        return qualification;
    }

    // Entity -> Response DTO
    public static QualificationResponseDto toDto(Qualification qualification) {

        QualificationResponseDto dto = new QualificationResponseDto();

        dto.setQualificationId(qualification.getQualificationId());
        dto.setQualificationName(qualification.getQualificationName());

        dto.setDoctorCount(
                qualification.getDoctors()==null ?
                        0 :
                        qualification.getDoctors().size());


        dto.setCreatedAt(qualification.getCreatedAt());
        dto.setCreatedBy(qualification.getCreatedBy());

        dto.setUpdatedAt(qualification.getUpdatedAt());
        dto.setUpdatedBy(qualification.getUpdatedBy());

        return dto;
    }
}
