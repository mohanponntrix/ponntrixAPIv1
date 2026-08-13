package com.ponntrix.hospital.dto.requestDto;

import lombok.Data;

@Data
public class DepartmentRequestDto {
    private String departmentCode;

    private String departmentName;

    private Integer createdBy;

    private Integer updatedBy;
}
