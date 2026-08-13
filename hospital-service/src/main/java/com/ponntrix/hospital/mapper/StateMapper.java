package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.requestDto.StateRequestDto;
import com.ponntrix.hospital.dto.responseDto.StateResponseDto;
import com.ponntrix.hospital.entity.State;

public class StateMapper {

    public static State toEntity(StateRequestDto dto) {

        State state = new State();

        state.setStateName(dto.getStateName());
        state.setCreatedBy(dto.getCreatedBy());
        state.setUpdatedBy(dto.getUpdatedBy());

        return state;
    }

    public static StateResponseDto toDto(State state) {

        StateResponseDto dto = new StateResponseDto();
        dto.setStateId(state.getStateId());
        dto.setStateName(state.getStateName());

        if (state.getCountry() != null) {
            dto.setCountryId(state.getCountry().getCountryId());
            dto.setCountryName(state.getCountry().getCountryName());
        }

        dto.setCreatedAt(state.getCreatedAt());
        dto.setUpdatedAt(state.getUpdatedAt());
        dto.setCreatedBy(state.getCreatedBy());
        dto.setUpdatedBy(state.getUpdatedBy());

        return dto;
    }
}
