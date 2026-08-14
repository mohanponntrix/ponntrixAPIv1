package com.ponntrix.hospital.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class DepartmentResponseDto {
    private Integer departmentId;

    private String departmentCode;

    private String departmentName;

    private String departmentIconUrl;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;
}
