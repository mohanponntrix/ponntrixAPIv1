package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.requestDto.DoctorSpecializationRequestDto;
import com.ponntrix.hospital.dto.responseDto.DoctorSpecializationResponseDto;

import java.util.List;

public interface DoctorSpecializationService {

    DoctorSpecializationResponseDto addSpecialization(
            DoctorSpecializationRequestDto dto);

    List<DoctorSpecializationResponseDto> getDoctorSpecializations(
            Integer doctorId);

    DoctorSpecializationResponseDto updateSpecialization(
            Integer doctorSpecializationId,
            DoctorSpecializationRequestDto dto);

    void removeSpecialization(
            Integer doctorSpecializationId);

    List<DoctorSpecializationResponseDto> getAllDoctorSpecializations();
}
