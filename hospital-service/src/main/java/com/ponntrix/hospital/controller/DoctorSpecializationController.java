package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.DoctorSpecializationDto;
import com.ponntrix.hospital.service.DoctorSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctor-specializations")
@RequiredArgsConstructor
public class DoctorSpecializationController {

    private final DoctorSpecializationService doctorSpecializationService;

    @PostMapping("/create")
    public ResponseEntity<DoctorSpecializationDto> addSpecialization(
            @RequestBody DoctorSpecializationDto dto) {

        return ResponseEntity.ok(
                doctorSpecializationService.addSpecialization(dto));
    }

    @GetMapping
    public List<DoctorSpecializationDto>
    getAllDoctorSpecializations() {
        return doctorSpecializationService.getAllDoctorSpecializations();
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<DoctorSpecializationDto>>
    getDoctorSpecializations(
            @PathVariable UUID doctorId) {

        return ResponseEntity.ok(
                doctorSpecializationService.getDoctorSpecializations(doctorId));
    }

    @PutMapping("/{doctorSpecializationId}")
    public ResponseEntity<DoctorSpecializationDto>
    updateSpecialization(
            @PathVariable UUID doctorSpecializationId,
            @RequestBody DoctorSpecializationDto dto) {

        return ResponseEntity.ok(
                doctorSpecializationService.updateSpecialization(
                        doctorSpecializationId,
                        dto));
    }

    @DeleteMapping("/{doctorSpecializationId}")
    public ResponseEntity<String> removeSpecialization(
            @PathVariable UUID doctorSpecializationId) {

        doctorSpecializationService.removeSpecialization(
                doctorSpecializationId);

        return ResponseEntity.ok(
                "Doctor specialization removed successfully.");
    }
}
