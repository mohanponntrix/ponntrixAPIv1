package com.ponntrix.hospital.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AreaRequestDto {

    private String areaName;

    private Integer cityId;

    private Integer createdBy;

    private Integer updatedBy;

}
