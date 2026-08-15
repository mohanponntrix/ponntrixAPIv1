package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.OperatingHoursDto;
import com.ponntrix.hospital.entity.OperatingHours;

public class OperatingHoursMapper {

    public static OperatingHours toEntity(OperatingHoursDto dto) {

        OperatingHours entity = new OperatingHours();

        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId());
        entity.setOpeningTime(dto.getOpeningTime());
        entity.setClosingTime(dto.getClosingTime());
        entity.setIsClosed(dto.getIsClosed());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        return entity;
    }

    public static OperatingHoursDto toDto(OperatingHours entity) {

        OperatingHoursDto dto = new OperatingHoursDto();

        dto.setOperatingHoursUUID(entity.getOperatingHoursUuid());
        dto.setEntityType(entity.getEntityType());
        dto.setEntityId(entity.getEntityId());
        if (entity.getDayOfWeek() != null) {
            dto.setDayOfWeekId(entity.getDayOfWeek().getDayOfWeekId());
            dto.setDayName(entity.getDayOfWeek().getDayName());
        }
        dto.setOpeningTime(entity.getOpeningTime());
        dto.setClosingTime(entity.getClosingTime());
        dto.setIsClosed(entity.getIsClosed());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }
}