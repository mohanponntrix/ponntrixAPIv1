package com.ponntrix.hospital.service;


import com.ponntrix.hospital.dto.request.AreaRequestDto;
import com.ponntrix.hospital.dto.response.AreaResponseDto;

import java.util.List;

public interface AreaService {

    AreaResponseDto createArea(AreaRequestDto dto);

    List<AreaResponseDto> getAllAreas();

    AreaResponseDto getAreaById(Integer areaId);

    AreaResponseDto updateArea(Integer areaId, AreaRequestDto dto);

    void deleteArea(Integer areaId);

}
