package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.request.DocumentsRequestDto;
import com.ponntrix.hospital.dto.request.HospitalRequestDto;
import com.ponntrix.hospital.dto.response.DocumentsResponseDto;
import com.ponntrix.hospital.dto.response.HospitalResponseDto;
import com.ponntrix.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HospitalResponseDto> createHospital(
            @RequestPart("hospital") String hospitalJson,
            @RequestPart("logo") MultipartFile logo,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "documentFiles", required = false)
            List<MultipartFile> documentFiles) throws IOException {

        HospitalRequestDto dto =
                objectMapper.readValue(
                        hospitalJson,
                        HospitalRequestDto.class
                );

        return ResponseEntity.ok(hospitalService.createHospital(dto, logo, coverImage,documentFiles));
    }


    @GetMapping
    public ResponseEntity<List<HospitalResponseDto>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }


    @GetMapping("/{hospitalId}")
    public ResponseEntity<HospitalResponseDto> getHospitalById(@PathVariable Integer hospitalId) {
        return ResponseEntity.ok(hospitalService.getHospitalById(hospitalId));
    }


    @PutMapping(value = "/{hospitalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HospitalResponseDto> updateHospital(
            @PathVariable Integer hospitalId,
            @RequestPart("hospital") String hospitalJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "documentFiles", required = false)
            List<MultipartFile> documentFiles)throws IOException {

        HospitalRequestDto dto =
                objectMapper.readValue(
                        hospitalJson,
                        HospitalRequestDto.class
                );

        return ResponseEntity.ok(hospitalService.updateHospital(
                hospitalId, dto, logo, coverImage,documentFiles));
    }

    @PostMapping(value = "/{hospitalId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentsResponseDto> uploadHospitalDocument(
            @PathVariable Integer hospitalId,
            @RequestPart("document") String documentJson,
            @RequestPart("file") MultipartFile file) throws IOException {

        DocumentsRequestDto dto = objectMapper.readValue(documentJson, DocumentsRequestDto.class);

        return ResponseEntity.ok(hospitalService.uploadHospitalDocument(hospitalId, dto, file));
    }


    @DeleteMapping("/{hospitalId}")
    public ResponseEntity<String> deleteHospital(@PathVariable Integer hospitalId) {
        hospitalService.deleteHospital(hospitalId);
        return ResponseEntity.noContent().build();
    }

}
