package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.repository.DepartmentRepository;
import com.ponntrix.hospital.dto.requestDto.DepartmentRequestDto;
import com.ponntrix.hospital.dto.responseDto.DepartmentResponseDto;
import com.ponntrix.hospital.service.DepartmentsService;
import com.ponntrix.hospital.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentsService departmentsService;

    //Fetching already existing all Departments
    @GetMapping
    public List<DepartmentResponseDto> getAllDepartments() {

        return departmentsService.getAllDepartments();
    }

    //Creating a new department
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DepartmentResponseDto> createDepartment(
            @ModelAttribute DepartmentRequestDto requestDto,
            @RequestParam MultipartFile icon) throws IOException {

       return ResponseEntity.ok(
               departmentsService.createDepartment(requestDto,icon)
       );
    }

    //Fetching particular Department by id
    @GetMapping("/{id}")
    public DepartmentResponseDto getDepartmentById(@PathVariable Integer id) {

        return departmentsService.getDepartmentById(id);
    }

    //Updating particular Department by id
    @PutMapping(value = "update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DepartmentResponseDto> updateDepartment(
            @PathVariable Integer id,
            @ModelAttribute DepartmentRequestDto dto,
            @RequestParam(required = false) MultipartFile icon)throws IOException {

        return ResponseEntity.ok(
                departmentsService.updateDepartment(id,dto,icon)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDepartment(
            @PathVariable Integer id) {

        departmentsService.deleteDepartment(id);

        return ResponseEntity.ok("Department deleted successfully.");
    }

}
