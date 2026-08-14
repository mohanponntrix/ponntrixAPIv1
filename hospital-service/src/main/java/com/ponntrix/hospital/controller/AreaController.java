package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.entity.Area;
import com.ponntrix.hospital.repository.AreaRepository;
import com.ponntrix.hospital.dto.request.AreaRequestDto;
import com.ponntrix.hospital.dto.response.AreaResponseDto;
import com.ponntrix.hospital.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;
    private final AreaRepository areaRepository;

    @PostMapping("/create")
    public ResponseEntity<AreaResponseDto> createArea(
            @RequestBody AreaRequestDto dto) {
        return ResponseEntity.ok(areaService.createArea(dto));
    }

    @GetMapping
    public ResponseEntity<List<AreaResponseDto>> getAllAreas() {
        return ResponseEntity.ok(areaService.getAllAreas());
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Area>> getAreaByCity(
            @PathVariable Integer cityId){
        return ResponseEntity.ok(
                areaRepository.findByCityCityId(cityId)
        );
    }

    @GetMapping("/{areaId}")
    public ResponseEntity<AreaResponseDto> getAreaById(
            @PathVariable Integer areaId) {
        return ResponseEntity.ok(areaService.getAreaById(areaId));
    }

    @PutMapping("/{areaId}")
    public ResponseEntity<AreaResponseDto> updateArea(
            @PathVariable Integer areaId,
            @RequestBody AreaRequestDto dto) {
        return ResponseEntity.ok(areaService.updateArea(areaId, dto));
    }


    @DeleteMapping("/{areaId}")
    public ResponseEntity<String> deleteArea(@PathVariable Integer areaId) {
        areaService.deleteArea(areaId);
        return ResponseEntity.ok("Area deleted successfully.");
    }

}
