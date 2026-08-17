package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.DoctorSpecializationDto;

import java.util.List;
import java.util.UUID;

public interface DoctorSpecializationService {

    DoctorSpecializationDto addSpecialization(
            DoctorSpecializationDto dto);

    List<DoctorSpecializationDto> getDoctorSpecializations(
            UUID doctorId);

    DoctorSpecializationDto updateSpecialization(
            UUID doctorSpecializationId,
            DoctorSpecializationDto dto);

    void removeSpecialization(UUID doctorSpecializationId);

    List<DoctorSpecializationDto> getAllDoctorSpecializations();
}
