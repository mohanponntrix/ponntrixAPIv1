package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.request.DocumentsRequestDto;
import com.ponntrix.hospital.dto.request.HospitalRequestDto;
import com.ponntrix.hospital.dto.response.DocumentsResponseDto;
import com.ponntrix.hospital.dto.response.HospitalResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HospitalService {

    HospitalResponseDto createHospital(
            HospitalRequestDto dto,
            MultipartFile logo,
            MultipartFile coverImage,
            List<MultipartFile> documentFiles) throws IOException;

    List<HospitalResponseDto> getAllHospitals();

    HospitalResponseDto getHospitalById(Integer hospitalId);

    HospitalResponseDto updateHospital(
            Integer hospitalId,
            HospitalRequestDto dto,
            MultipartFile logo,
            MultipartFile coverImage,
            List<MultipartFile> documentFiles) throws IOException;


    DocumentsResponseDto uploadHospitalDocument(
            Integer hospitalId,
            DocumentsRequestDto dto,
            MultipartFile file
    ) throws IOException;

    void deleteHospital(Integer hospitalId);

}
