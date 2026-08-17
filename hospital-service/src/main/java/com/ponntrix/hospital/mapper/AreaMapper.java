package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.entity.locations.Area;
import com.ponntrix.hospital.dto.request.AreaRequestDto;
import com.ponntrix.hospital.dto.response.AreaResponseDto;

public class AreaMapper {

    public static Area toEntity(AreaRequestDto dto) {

        Area area = new Area();

        area.setAreaName(dto.getAreaName());
        area.setCreatedBy(dto.getCreatedBy());
        area.setUpdatedBy(dto.getUpdatedBy());

        return area;
    }

    public static AreaResponseDto toDto(Area area) {

        AreaResponseDto dto = new AreaResponseDto();

        dto.setAreaId(area.getAreaId());
        dto.setAreaName(area.getAreaName());

        if (area.getCity() != null) {

            dto.setCityId(area.getCity().getCityId());
            dto.setCityName(area.getCity().getCityName());

        }

        dto.setCreatedAt(area.getCreatedAt());
        dto.setUpdatedAt(area.getUpdatedAt());
        dto.setCreatedBy(area.getCreatedBy());
        dto.setUpdatedBy(area.getUpdatedBy());

        return dto;
    }

}
