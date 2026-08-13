package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.requestDto.*;
import com.ponntrix.hospital.dto.responseDto.DoctorResponseDto;
import com.ponntrix.hospital.dto.responseDto.DoctorSpecializationResponseDto;
import com.ponntrix.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping(value="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DoctorResponseDto createDoctor(
            @ModelAttribute DoctorRequestDto dto,
            @RequestPart(value = "profilePic",required = false)
            MultipartFile profilePic,
            @RequestPart(value = "digitalSignature",required = false)
            MultipartFile digitalSignature) throws IOException {
        return doctorService.createDoctor(dto,profilePic,digitalSignature);
    }


    @GetMapping
    public List<DoctorResponseDto> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{doctorId}")
    public DoctorResponseDto getDoctorById(@PathVariable Integer doctorId){
        return doctorService.getDoctorById(doctorId);
    }


    @PutMapping(value="/update/{doctorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DoctorResponseDto updateDoctor(
            @PathVariable Integer doctorId,
            @ModelAttribute DoctorRequestDto dto,
            @RequestPart(value = "profilePic",required = false)
            MultipartFile profilePic,
            @RequestPart(value = "digitalSignature",required = false)
            MultipartFile digitalSignature) throws IOException {
        return doctorService.updateDoctor(doctorId,dto,profilePic,digitalSignature);
    }


    @PatchMapping("/{doctorId}/delete/{updatedBy}")
    public ResponseEntity<String> softDeleteDoctor(
            @PathVariable Integer doctorId,
            @PathVariable Integer updatedBy) {
        doctorService.softDeleteDoctor(doctorId, updatedBy);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Integer doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }


    @PatchMapping("/{doctorId}/restore/{updatedBy}")
    public ResponseEntity<String> restoreDoctor(
            @PathVariable Integer doctorId,
            @PathVariable Integer updatedBy) {
        doctorService.restoreDoctor(doctorId, updatedBy);
        return ResponseEntity.ok("Doctor restored successfully.");
    }

    @PostMapping("/{doctorId}/specializations")
    public ResponseEntity<List<DoctorSpecializationResponseDto>>
    updateDoctorSpecializations(
            @PathVariable Integer doctorId,
            @RequestBody
            List<DoctorSpecializationRequestDto> specializations) {

        return ResponseEntity.ok(doctorService.updateDoctorSpecializations(doctorId, specializations));
    }

}