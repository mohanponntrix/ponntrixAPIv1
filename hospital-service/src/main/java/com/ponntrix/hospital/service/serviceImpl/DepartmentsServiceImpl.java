package com.ponntrix.hospital.service.serviceImpl;


import com.ponntrix.hospital.dto.requestDto.DepartmentRequestDto;
import com.ponntrix.hospital.dto.responseDto.DepartmentResponseDto;
import com.ponntrix.hospital.entity.Department;
import com.ponntrix.hospital.exception.ResourceNotFoundException;
import com.ponntrix.hospital.mapper.DepartmentMapper;
import com.ponntrix.hospital.repository.DepartmentRepository;
import com.ponntrix.hospital.service.DepartmentsService;
import com.ponntrix.hospital.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentsServiceImpl implements DepartmentsService {

    private final DepartmentRepository departmentRepository;
    private final S3Service s3Service;

    //creating a new Department
    @Override
    public DepartmentResponseDto createDepartment(
            DepartmentRequestDto dto, MultipartFile icon) throws IOException {

        String folder = "department-icons";

        String imageUrl = s3Service.uploadFile(icon,folder);

        Department department = DepartmentMapper.toEntity(dto);

        department.setDepartmentIconUrl(imageUrl);

        Department saved = departmentRepository.save(department);

        return DepartmentMapper.toDto(saved);
    }

    //Fetching all Departments
    @Override
    public List<DepartmentResponseDto> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(DepartmentMapper::toDto)
                .toList();
    }

    //Fetching particular department by id
    @Override
    public DepartmentResponseDto getDepartmentById(Integer id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        return DepartmentMapper.toDto(department);
    }

    //update particular Department by id
    @Override
    public DepartmentResponseDto updateDepartment(Integer id,
                                                  DepartmentRequestDto dto,
                                                  MultipartFile icon) throws IOException {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        department.setDepartmentCode(dto.getDepartmentCode());
        department.setDepartmentName(dto.getDepartmentName());

        if (icon != null && !icon.isEmpty()) {

            // Delete old image
            if (department.getDepartmentIconUrl() != null) {
                s3Service.deleteFile(department.getDepartmentIconUrl());
            }

            // Upload new image
            String folder = "department-icons";
            String imageUrl = s3Service.uploadFile(icon,folder);

            department.setDepartmentIconUrl(imageUrl);
        }

        department.setUpdatedBy(dto.getUpdatedBy());
        department.setUpdatedAt(OffsetDateTime.now());

        Department updated = departmentRepository.save(department);

        return DepartmentMapper.toDto(updated);
    }

    @Override
    public void deleteDepartment(Integer id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Delete image from S3
        if (department.getDepartmentIconUrl() != null) {
            s3Service.deleteFile(department.getDepartmentIconUrl());
        }

        // Delete from database
        departmentRepository.delete(department);
    }


}

