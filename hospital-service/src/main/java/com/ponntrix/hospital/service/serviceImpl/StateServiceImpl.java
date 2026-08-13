package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.entity.Country;
import com.ponntrix.hospital.entity.State;
import com.ponntrix.hospital.mapper.StateMapper;
import com.ponntrix.hospital.repository.CountryRepository;
import com.ponntrix.hospital.repository.StateRepository;
import com.ponntrix.hospital.dto.requestDto.StateRequestDto;
import com.ponntrix.hospital.dto.responseDto.StateResponseDto;
import com.ponntrix.hospital.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    @Override
    public StateResponseDto createState(StateRequestDto dto) {

        if (stateRepository.existsByStateNameIgnoreCase(dto.getStateName())) {
            throw new RuntimeException("State already exists.");
        }

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found."));

        State state = StateMapper.toEntity(dto);
        state.setCountry(country);
        State savedState = stateRepository.save(state);

        return StateMapper.toDto(savedState);
    }


    @Override
    public List<StateResponseDto> getAllStates() {

        return stateRepository.findAll()
                .stream()
                .map(StateMapper::toDto)
                .toList();
    }


    @Override
    public StateResponseDto getStateById(Integer stateId) {

        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("State not found."));

        return StateMapper.toDto(state);
    }


    @Override
    public StateResponseDto updateState(
            Integer stateId,
            StateRequestDto dto) {

        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("State not found."));

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found."));

        state.setStateName(dto.getStateName());
        state.setCountry(country);
        state.setUpdatedBy(dto.getUpdatedBy());

        State updatedState = stateRepository.save(state);

        return StateMapper.toDto(updatedState);
    }


    @Override
    public void deleteState(Integer stateId) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("State not found."));
        stateRepository.delete(state);
    }
}
