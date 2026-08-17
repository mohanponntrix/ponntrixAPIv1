package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.DocumentsDto;
import com.ponntrix.hospital.dto.request.HospitalRequestDto;
import com.ponntrix.hospital.dto.response.HospitalResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface HospitalService {

    HospitalResponseDto createHospital(
            HospitalRequestDto dto,
            MultipartFile logo,
            MultipartFile coverImage,
            List<MultipartFile> documentFiles) throws IOException;

    List<HospitalResponseDto> getAllHospitals();

    HospitalResponseDto getHospitalById(UUID hospitalUUID);

    HospitalResponseDto updateHospital(
            UUID hospitalId,
            HospitalRequestDto dto,
            MultipartFile logo,
            MultipartFile coverImage,
            List<MultipartFile> documentFiles) throws IOException;


    DocumentsDto uploadHospitalDocument(
            UUID hospitalId,
            DocumentsDto dto,
            MultipartFile file
    ) throws IOException;

    void deleteHospital(UUID hospitalId);

}
