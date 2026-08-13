package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.requestDto.StateRequestDto;
import com.ponntrix.hospital.dto.responseDto.StateResponseDto;

import java.util.List;

public interface StateService {

    StateResponseDto createState(StateRequestDto dto);

    List<StateResponseDto> getAllStates();

    StateResponseDto getStateById(Integer stateId);

    StateResponseDto updateState(
            Integer stateId,
            StateRequestDto dto);

    void deleteState(Integer stateId);

}
