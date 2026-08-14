package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.dto.request.DepartmentRequestDto;
import com.ponntrix.hospital.dto.response.DepartmentResponseDto;
import com.ponntrix.hospital.entity.Department;

public class DepartmentMapper {

    public static Department toEntity(DepartmentRequestDto dto) {
        Department department = new Department();
        department.setDepartmentCode(dto.getDepartmentCode());
        department.setDepartmentName(dto.getDepartmentName());
        department.setCreatedBy(dto.getCreatedBy());
        department.setUpdatedBy(dto.getUpdatedBy());

        return department;
    }

    public static DepartmentResponseDto toDto(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();

        dto.setDepartmentId(department.getDepartment_id());
        dto.setDepartmentCode(department.getDepartmentCode());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDepartmentIconUrl(department.getDepartmentIconUrl());
        dto.setCreatedAt(department.getCreatedAt());
        dto.setUpdatedAt(department.getUpdatedAt());
        dto.setCreatedBy(department.getCreatedBy());
        dto.setUpdatedBy(department.getUpdatedBy());

        return dto;
    }

}