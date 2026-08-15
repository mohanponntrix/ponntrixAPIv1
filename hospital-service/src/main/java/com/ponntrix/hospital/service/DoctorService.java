package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.request.DoctorRequestDto;
import com.ponntrix.hospital.dto.DoctorSpecializationDto;
import com.ponntrix.hospital.dto.response.DoctorResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DoctorService {

    DoctorResponseDto createDoctor(
            DoctorRequestDto dto,
            MultipartFile profilePic,
            MultipartFile digitalSignature) throws IOException;

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto getDoctorById(UUID doctorId);

    DoctorResponseDto updateDoctor(
            UUID doctorId,
            DoctorRequestDto dto,
            MultipartFile profilePic,
            MultipartFile digitalSignature) throws IOException;

    void deleteDoctor(UUID doctorId);

    List<DoctorSpecializationDto> updateDoctorSpecializations(
            UUID doctorId,
            List<DoctorSpecializationDto> specializations);}
