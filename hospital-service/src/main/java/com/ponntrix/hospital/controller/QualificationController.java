package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.requestDto.QualificationRequestDto;
import com.ponntrix.hospital.dto.responseDto.QualificationResponseDto;
import com.ponntrix.hospital.service.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qualifications")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class QualificationController {

    private final QualificationService qualificationService;

    // Create Qualification
    @PostMapping("/create")
    public ResponseEntity<QualificationResponseDto> createQualification(
            @RequestBody QualificationRequestDto dto) {

        QualificationResponseDto response =
                qualificationService.createQualification(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get All Qualifications
    @GetMapping
    public ResponseEntity<List<QualificationResponseDto>> getAllQualifications() {
        return ResponseEntity.ok(qualificationService.getAllQualifications());
    }

    // Get Qualification By Id
    @GetMapping("/{id}")
    public ResponseEntity<QualificationResponseDto> getQualificationById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                qualificationService.getQualificationById(id));
    }

    // Update Qualification
    @PutMapping("/update/{id}")
    public ResponseEntity<QualificationResponseDto> updateQualification(
            @PathVariable Integer id,
            @RequestBody QualificationRequestDto dto) {

        return ResponseEntity.ok(
                qualificationService.updateQualification(id, dto));
    }

    // Delete Qualification
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQualification(@PathVariable Integer id) {

        qualificationService.deleteQualification(id);

        return ResponseEntity.ok("Qualification deleted successfully.");
    }

}