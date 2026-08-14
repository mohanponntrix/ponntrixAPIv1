package com.ponntrix.hospital.serviceimpl;

import com.ponntrix.hospital.entity.Doctor;
import com.ponntrix.hospital.entity.DoctorSpecialization;
import com.ponntrix.hospital.entity.Specialization;
import com.ponntrix.hospital.mapper.DoctorSpecializationMapper;
import com.ponntrix.hospital.repository.DoctorRepository;
import com.ponntrix.hospital.repository.DoctorSpecializationRepository;
import com.ponntrix.hospital.repository.SpecializationRepository;
import com.ponntrix.hospital.dto.request.DoctorSpecializationRequestDto;
import com.ponntrix.hospital.dto.response.DoctorSpecializationResponseDto;
import com.ponntrix.hospital.service.DoctorSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorSpecializationServiceImpl implements DoctorSpecializationService {

    private final DoctorSpecializationRepository doctorSpecializationRepository;
    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    @Override
    public DoctorSpecializationResponseDto addSpecialization(
            DoctorSpecializationRequestDto dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found."));

        Specialization specialization =
                specializationRepository.findById(dto.getSpecializationId())
                        .orElseThrow(() ->
                                new RuntimeException("Specialization not found."));

        if (doctorSpecializationRepository
                .existsByDoctorDoctorIdAndSpecializationSpecializationId(
                        dto.getDoctorId(),
                        dto.getSpecializationId())) {

            throw new RuntimeException(
                    "Specialization already assigned to doctor.");
        }

        // Only one primary specialization
        if (Boolean.TRUE.equals(dto.getIsPrimary())) {

            doctorSpecializationRepository
                    .findByDoctorDoctorIdAndIsPrimaryTrue(dto.getDoctorId())
                    .ifPresent(existing -> {

                        existing.setIsPrimary(false);

                        doctorSpecializationRepository.save(existing);

                    });
        }

        DoctorSpecialization doctorSpecialization =
                DoctorSpecializationMapper.toEntity(dto);

        doctorSpecialization.setDoctor(doctor);
        doctorSpecialization.setSpecialization(specialization);

        DoctorSpecialization saved =
                doctorSpecializationRepository.save(doctorSpecialization);

        return DoctorSpecializationMapper.toDto(saved);
    }


    @Override
    public List<DoctorSpecializationResponseDto> getDoctorSpecializations(
            Integer doctorId) {

        return doctorSpecializationRepository
                .findByDoctorDoctorId(doctorId)
                .stream()
                .map(DoctorSpecializationMapper::toDto)
                .toList();
    }

    @Override
    public List<DoctorSpecializationResponseDto> getAllDoctorSpecializations(){
     return doctorSpecializationRepository.findAll()
             .stream()
             .map(DoctorSpecializationMapper::toDto)
             .toList();
    }


    @Override
    public DoctorSpecializationResponseDto updateSpecialization(
            Integer doctorSpecializationId,
            DoctorSpecializationRequestDto dto) {

        DoctorSpecialization doctorSpecialization =
                doctorSpecializationRepository.findById(
                                doctorSpecializationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Doctor specialization not found."));

        Specialization specialization =
                specializationRepository.findById(dto.getSpecializationId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Specialization not found."));

        if (Boolean.TRUE.equals(dto.getIsPrimary())) {

            doctorSpecializationRepository
                    .findByDoctorDoctorIdAndIsPrimaryTrue(
                            doctorSpecialization.getDoctor().getDoctorId())
                    .ifPresent(existing -> {

                        if (!existing.getDoctorSpecializationsId()
                                .equals(doctorSpecializationId)) {

                            existing.setIsPrimary(false);

                            doctorSpecializationRepository.save(existing);
                        }

                    });
        }

        doctorSpecialization.setSpecialization(specialization);
        doctorSpecialization.setIsPrimary(dto.getIsPrimary());
        doctorSpecialization.setUpdatedBy(dto.getUpdatedBy());

        DoctorSpecialization updated =
                doctorSpecializationRepository.save(doctorSpecialization);

        return DoctorSpecializationMapper.toDto(updated);
    }


    @Override
    public void removeSpecialization(Integer doctorSpecializationId) {

        DoctorSpecialization doctorSpecialization =
                doctorSpecializationRepository.findById(
                                doctorSpecializationId)
                        .orElseThrow(() -> new RuntimeException(
                                        "Doctor specialization not found."));

        doctorSpecializationRepository.delete(doctorSpecialization);
    }

}
