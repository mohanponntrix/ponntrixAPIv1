package com.ponntrix.hospital.dto.request;

import lombok.Data;

@Data
public class DepartmentRequestDto {
    private String departmentCode;

    private String departmentName;

    private Integer createdBy;

    private Integer updatedBy;
}
