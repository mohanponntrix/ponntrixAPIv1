package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.request.DoctorRequestDto;
import com.ponntrix.hospital.dto.response.DoctorResponseDto;
import com.ponntrix.hospital.entity.Doctor;

public class DoctorMapper {

    public static Doctor toEntity(DoctorRequestDto dto) {

        Doctor doctor = new Doctor();

        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setGender(dto.getGender());
        doctor.setDoctorPhone(dto.getDoctorPhone());
        doctor.setDoctorEmail(dto.getDoctorEmail());
        doctor.setRegistrationNumber(dto.getRegistrationNumber());
        doctor.setExperienceInYears(dto.getExperienceInYears());
        doctor.setCreatedBy(dto.getCreatedBy());
        doctor.setUpdatedBy(dto.getUpdatedBy());

        return doctor;
    }

    public static DoctorResponseDto toDto(Doctor doctor) {

        DoctorResponseDto dto = new DoctorResponseDto();

        dto.setDoctorId(doctor.getDoctorId());
        dto.setDoctorUUID(doctor.getDoctorUUID());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setGender(doctor.getGender());
        dto.setDoctorPhone(doctor.getDoctorPhone());
        dto.setDoctorEmail(doctor.getDoctorEmail());

        if (doctor.getQualification() != null) {
            dto.setQualificationId(doctor.getQualification().getQualificationId());
            dto.setQualificationName(doctor.getQualification().getQualificationName());
        }

        dto.setRegistrationNumber(doctor.getRegistrationNumber());
        dto.setExperienceInYears(doctor.getExperienceInYears());
        dto.setProfilePicUrl(doctor.getProfilePicUrl());
        dto.setDigitalSignatureUrl(doctor.getDigitalSignatureUrl());
        dto.setIsActive(doctor.getIsActive());
        dto.setCreatedAt(doctor.getCreatedAt());
        dto.setUpdatedAt(doctor.getUpdatedAt());
        dto.setCreatedBy(doctor.getCreatedBy());
        dto.setUpdatedBy(doctor.getUpdatedBy());

        return dto;
    }

}