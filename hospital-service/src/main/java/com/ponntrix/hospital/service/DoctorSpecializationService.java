package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.request.DoctorSpecializationRequestDto;
import com.ponntrix.hospital.dto.response.DoctorSpecializationResponseDto;

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
