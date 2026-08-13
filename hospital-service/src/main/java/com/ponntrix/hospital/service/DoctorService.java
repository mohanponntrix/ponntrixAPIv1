package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.requestDto.DoctorRequestDto;
import com.ponntrix.hospital.dto.requestDto.DoctorSpecializationRequestDto;
import com.ponntrix.hospital.dto.responseDto.DoctorResponseDto;
import com.ponntrix.hospital.dto.responseDto.DoctorSpecializationResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DoctorService {

    DoctorResponseDto createDoctor(
            DoctorRequestDto dto,
            MultipartFile profilePic,
            MultipartFile digitalSignature) throws IOException;

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto getDoctorById(Integer doctorId);

    DoctorResponseDto updateDoctor(
            Integer doctorId,
            DoctorRequestDto dto,
            MultipartFile profilePic,
            MultipartFile digitalSignature) throws IOException;

    void softDeleteDoctor(Integer doctorId, Integer updatedBy);

    void deleteDoctor(Integer doctorId);

    void restoreDoctor(Integer doctorId, Integer updatedBy);

    List<DoctorSpecializationResponseDto> updateDoctorSpecializations(
            Integer doctorId,
            List<DoctorSpecializationRequestDto> specializations);}