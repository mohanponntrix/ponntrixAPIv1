package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.OperatingHoursDto;
import com.ponntrix.hospital.entity.EntityType;

import java.util.List;
import java.util.UUID;

public interface OperatingHoursService {

    OperatingHoursDto createOperatingHours(OperatingHoursDto dto);

    List<OperatingHoursDto> getOperatingHours(EntityType entityType, UUID entityId);

    OperatingHoursDto updateOperatingHours(UUID operatingHoursId, OperatingHoursDto dto);

    void deleteOperatingHours(UUID operatingHoursId);

    void deleteEntityOperatingHours(EntityType entityType, UUID entityId);

}
