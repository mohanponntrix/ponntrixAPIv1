package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.requestDto.SpecializationRequestDto;
import com.ponntrix.hospital.dto.responseDto.SpecializationResponseDto;

import java.util.List;

public interface SpecializationService {

    SpecializationResponseDto createSpecialization(SpecializationRequestDto dto);

    List<SpecializationResponseDto> getAllSpecializations();

    SpecializationResponseDto getSpecializationById(Integer specializationId);

    SpecializationResponseDto updateSpecialization(
            Integer specializationId,
            SpecializationRequestDto dto);

    void deleteSpecialization(Integer specializationId);

}
