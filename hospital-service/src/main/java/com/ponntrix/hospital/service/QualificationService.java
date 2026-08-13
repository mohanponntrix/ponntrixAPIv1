package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.requestDto.QualificationRequestDto;
import com.ponntrix.hospital.dto.responseDto.QualificationResponseDto;

import java.util.List;

public interface QualificationService{
    // Create Qualification
    QualificationResponseDto createQualification(QualificationRequestDto dto);

    // Get All Qualifications
    List<QualificationResponseDto> getAllQualifications();

    // Get Qualification By Id
    QualificationResponseDto getQualificationById(Integer qualificationId);

    // Update Qualification
    QualificationResponseDto updateQualification(Integer qualificationId, QualificationRequestDto dto);

    // Delete Qualification
    void deleteQualification(Integer qualificationId);

}
