package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.entity.State;
import com.ponntrix.hospital.repository.StateRepository;
import com.ponntrix.hospital.dto.requestDto.StateRequestDto;
import com.ponntrix.hospital.dto.responseDto.StateResponseDto;
import com.ponntrix.hospital.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;
    private final StateRepository stateRepository;

    @PostMapping("/create")
    public ResponseEntity<StateResponseDto> createState(
            @RequestBody StateRequestDto dto) {

        return ResponseEntity.ok(stateService.createState(dto));
    }

    @GetMapping
    public ResponseEntity<List<StateResponseDto>> getAllStates() {
        return ResponseEntity.ok(stateService.getAllStates());
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<State>> getStatesByCountry(
            @PathVariable Integer countryId) {

        return ResponseEntity.ok(
                stateRepository.findByCountry_CountryId(countryId)
        );
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<StateResponseDto> getStateById(@PathVariable Integer stateId) {
        return ResponseEntity.ok(stateService.getStateById(stateId));
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<StateResponseDto> updateState(
            @PathVariable Integer stateId,
            @RequestBody StateRequestDto dto) {

        return ResponseEntity.ok(stateService.updateState(stateId, dto));
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<String> deleteState(@PathVariable Integer stateId) {
        stateService.deleteState(stateId);
        return ResponseEntity.ok("State deleted successfully.");
    }
}
