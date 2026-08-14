package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.request.DepartmentRequestDto;
import com.ponntrix.hospital.dto.response.DepartmentResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DepartmentsService{
    DepartmentResponseDto createDepartment(
            DepartmentRequestDto dto,
            MultipartFile icon) throws IOException;

    List<DepartmentResponseDto> getAllDepartments();

    DepartmentResponseDto getDepartmentById(Integer id);

    DepartmentResponseDto updateDepartment(
            Integer id,
            DepartmentRequestDto dto,
            MultipartFile icon) throws IOException;

    void deleteDepartment(Integer id);

}
