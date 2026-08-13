package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.entity.Qualification;
import com.ponntrix.hospital.exception.DuplicateResourceException;
import com.ponntrix.hospital.exception.ResourceNotFoundException;
import com.ponntrix.hospital.mapper.QualificationMapper;
import com.ponntrix.hospital.repository.QualificationRepository;
import com.ponntrix.hospital.dto.requestDto.QualificationRequestDto;
import com.ponntrix.hospital.dto.responseDto.QualificationResponseDto;
import com.ponntrix.hospital.service.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;

    @Override
    public QualificationResponseDto createQualification(QualificationRequestDto dto) {

        if (qualificationRepository.existsByQualificationNameIgnoreCase(dto.getQualificationName())) {
            throw new DuplicateResourceException("Qualification already exists.");
        }

        Qualification qualification = QualificationMapper.toEntity(dto);

        Qualification savedQualification = qualificationRepository.save(qualification);

        return QualificationMapper.toDto(savedQualification);
    }

    @Override
    public List<QualificationResponseDto> getAllQualifications() {

        List<Qualification> qualifications = qualificationRepository.findAll();

        return qualifications.stream()
                .map(QualificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public QualificationResponseDto getQualificationById(Integer qualificationId) {

        Qualification qualification = qualificationRepository.findById(qualificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Qualification not found with id : " + qualificationId));

        return QualificationMapper.toDto(qualification);
    }

    @Override
    public QualificationResponseDto updateQualification(Integer qualificationId,
                                                        QualificationRequestDto dto) {

        Qualification qualification = qualificationRepository.findById(qualificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Qualification not found with id : " + qualificationId));

        if (!qualification.getQualificationName().equalsIgnoreCase(dto.getQualificationName())
                && qualificationRepository.existsByQualificationNameIgnoreCase(dto.getQualificationName())) {

            throw new DuplicateResourceException("Qualification already exists.");
        }

        qualification.setQualificationName(dto.getQualificationName());
        qualification.setUpdatedBy(dto.getUpdatedBy());

        Qualification updatedQualification = qualificationRepository.save(qualification);

        return QualificationMapper.toDto(updatedQualification);
    }

    @Override
    public void deleteQualification(Integer qualificationId) {

        Qualification qualification = qualificationRepository.findById(qualificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Qualification not found with id : " + qualificationId));

        if(!qualification.getDoctors().isEmpty()){
            throw new RuntimeException(
                    "Qualification is assigned to doctors.");
        }

        qualificationRepository.delete(qualification);
    }

}
