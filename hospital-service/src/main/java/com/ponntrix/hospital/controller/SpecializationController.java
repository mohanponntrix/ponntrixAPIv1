package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.requestDto.SpecializationRequestDto;
import com.ponntrix.hospital.dto.responseDto.SpecializationResponseDto;
import com.ponntrix.hospital.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @PostMapping("/create")
    public ResponseEntity<SpecializationResponseDto> createSpecialization(
            @RequestBody SpecializationRequestDto dto) {

        return ResponseEntity.ok(
                specializationService.createSpecialization(dto));
    }

    @GetMapping
    public ResponseEntity<List<SpecializationResponseDto>> getAllSpecializations() {

        return ResponseEntity.ok(
                specializationService.getAllSpecializations());
    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<SpecializationResponseDto> getSpecializationById(
            @PathVariable Integer specializationId) {

        return ResponseEntity.ok(
                specializationService.getSpecializationById(specializationId));
    }

    @PutMapping("/{specializationId}")
    public ResponseEntity<SpecializationResponseDto> updateSpecialization(
            @PathVariable Integer specializationId,
            @RequestBody SpecializationRequestDto dto) {

        return ResponseEntity.ok(
                specializationService.updateSpecialization(
                        specializationId,
                        dto));
    }

    @DeleteMapping("/{specializationId}")
    public ResponseEntity<String> deleteSpecialization(
            @PathVariable Integer specializationId) {

        specializationService.deleteSpecialization(specializationId);

        return ResponseEntity.ok(
                "Specialization deleted successfully.");
    }
}
