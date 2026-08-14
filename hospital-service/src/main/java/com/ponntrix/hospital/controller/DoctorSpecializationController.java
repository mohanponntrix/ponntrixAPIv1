package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.request.DoctorSpecializationRequestDto;
import com.ponntrix.hospital.dto.response.DoctorSpecializationResponseDto;
import com.ponntrix.hospital.service.DoctorSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-specializations")
@RequiredArgsConstructor
public class DoctorSpecializationController {

    private final DoctorSpecializationService doctorSpecializationService;

    @PostMapping("/create")
    public ResponseEntity<DoctorSpecializationResponseDto> addSpecialization(
            @RequestBody DoctorSpecializationRequestDto dto) {

        return ResponseEntity.ok(
                doctorSpecializationService.addSpecialization(dto));
    }

    @GetMapping
    public List<DoctorSpecializationResponseDto>
    getAllDoctorSpecializations() {
        return doctorSpecializationService.getAllDoctorSpecializations();
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<DoctorSpecializationResponseDto>>
    getDoctorSpecializations(
            @PathVariable Integer doctorId) {

        return ResponseEntity.ok(
                doctorSpecializationService.getDoctorSpecializations(doctorId));
    }

    @PutMapping("/{doctorSpecializationId}")
    public ResponseEntity<DoctorSpecializationResponseDto>
    updateSpecialization(
            @PathVariable Integer doctorSpecializationId,
            @RequestBody DoctorSpecializationRequestDto dto) {

        return ResponseEntity.ok(
                doctorSpecializationService.updateSpecialization(
                        doctorSpecializationId,
                        dto));
    }

    @DeleteMapping("/{doctorSpecializationId}")
    public ResponseEntity<String> removeSpecialization(
            @PathVariable Integer doctorSpecializationId) {

        doctorSpecializationService.removeSpecialization(
                doctorSpecializationId);

        return ResponseEntity.ok(
                "Doctor specialization removed successfully.");
    }
}
