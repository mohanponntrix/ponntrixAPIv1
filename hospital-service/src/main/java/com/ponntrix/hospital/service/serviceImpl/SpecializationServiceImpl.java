package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.entity.Specialization;
import com.ponntrix.hospital.mapper.SpecializationMapper;
import com.ponntrix.hospital.repository.SpecializationRepository;
import com.ponntrix.hospital.dto.requestDto.SpecializationRequestDto;
import com.ponntrix.hospital.dto.responseDto.SpecializationResponseDto;
import com.ponntrix.hospital.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Override
    public SpecializationResponseDto createSpecialization(SpecializationRequestDto dto) {

        if (specializationRepository
                .existsBySpecializationNameIgnoreCase(
                        dto.getSpecializationName())) {

            throw new RuntimeException(
                    "Specialization already exists.");
        }

        Specialization specialization =
                SpecializationMapper.toEntity(dto);

        Specialization saved =
                specializationRepository.save(specialization);

        return SpecializationMapper.toDto(saved);
    }

    @Override
    public List<SpecializationResponseDto> getAllSpecializations() {

        return specializationRepository.findAll()
                .stream()
                .map(SpecializationMapper::toDto)
                .toList();
    }


    @Override
    public SpecializationResponseDto getSpecializationById(
            Integer specializationId) {

        Specialization specialization =
                specializationRepository.findById(specializationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Specialization not found."));

        return SpecializationMapper.toDto(specialization);
    }

    @Override
    public SpecializationResponseDto updateSpecialization(
            Integer specializationId,
            SpecializationRequestDto dto) {

        Specialization specialization =
                specializationRepository.findById(specializationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Specialization not found."));

        specializationRepository
                .findBySpecializationNameIgnoreCase(
                        dto.getSpecializationName())
                .ifPresent(existing -> {

                    if (!existing.getSpecializationId()
                            .equals(specializationId)) {

                        throw new RuntimeException(
                                "Specialization already exists.");
                    }
                });

        specialization.setSpecializationName(
                dto.getSpecializationName());

        specialization.setUpdatedBy(
                dto.getUpdatedBy());

        Specialization updated =
                specializationRepository.save(specialization);

        return SpecializationMapper.toDto(updated);
    }

    @Override
    public void deleteSpecialization(
            Integer specializationId) {

        Specialization specialization =
                specializationRepository.findById(specializationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Specialization not found."));

        specializationRepository.delete(specialization);
    }
}
