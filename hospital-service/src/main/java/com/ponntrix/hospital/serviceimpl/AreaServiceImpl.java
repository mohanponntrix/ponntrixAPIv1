package com.ponntrix.hospital.serviceimpl;

import com.ponntrix.hospital.entity.Area;
import com.ponntrix.hospital.entity.City;
import com.ponntrix.hospital.mapper.AreaMapper;
import com.ponntrix.hospital.repository.AreaRepository;
import com.ponntrix.hospital.repository.CityRepository;
import com.ponntrix.hospital.dto.request.AreaRequestDto;
import com.ponntrix.hospital.dto.response.AreaResponseDto;
import com.ponntrix.hospital.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final CityRepository cityRepository;


    @Override
    public AreaResponseDto createArea(AreaRequestDto dto) {

        if (areaRepository.existsByAreaNameIgnoreCase(dto.getAreaName())) {
            throw new RuntimeException("Area already exists.");
        }

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found."));

        Area area = AreaMapper.toEntity(dto);
        area.setCity(city);
        Area savedArea = areaRepository.save(area);

        return AreaMapper.toDto(savedArea);
    }


    @Override
    public List<AreaResponseDto> getAllAreas() {

        return areaRepository.findAll()
                .stream()
                .map(AreaMapper::toDto)
                .toList();
    }


    @Override
    public AreaResponseDto getAreaById(Integer areaId) {

        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Area not found."));

        return AreaMapper.toDto(area);
    }


    @Override
    public AreaResponseDto updateArea(
            Integer areaId,
            AreaRequestDto dto) {

        Area area = areaRepository.findById(areaId)
                .orElseThrow(() ->
                        new RuntimeException("Area not found."));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() ->
                        new RuntimeException("City not found."));

        area.setAreaName(dto.getAreaName());
        area.setCity(city);
        area.setUpdatedBy(dto.getUpdatedBy());

        Area updatedArea = areaRepository.save(area);

        return AreaMapper.toDto(updatedArea);
    }


    @Override
    public void deleteArea(Integer areaId) {

        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Area not found."));
        areaRepository.delete(area);
    }

}
