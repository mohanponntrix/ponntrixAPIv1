package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.dto.HospitalFacilitiesDto;
import com.ponntrix.hospital.entity.Hospital;
import com.ponntrix.hospital.entity.HospitalFacilities;
import com.ponntrix.hospital.mapper.HospitalFacilitiesMapper;
import com.ponntrix.hospital.repository.HospitalFacilitiesRepository;
import com.ponntrix.hospital.repository.HospitalRepository;
import com.ponntrix.hospital.service.HospitalFacilitiesService;
import com.ponntrix.hospital.service.S3Service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalFacilitiesServiceImpl implements HospitalFacilitiesService {

    private final HospitalRepository hospitalRepository;
    private final HospitalFacilitiesRepository hospitalFacilitiesRepository;
    private final S3Service s3Service;


    @Override
    @Transactional
    public HospitalFacilitiesDto createFacility(
            UUID hospitalId,
            HospitalFacilitiesDto dto,
            MultipartFile icon) throws IOException {

        Hospital hospital = hospitalRepository.findByHospitalUUID(hospitalId)
                        .orElseThrow(() -> new RuntimeException("Hospital not found."));
        if (hospitalFacilitiesRepository.existsByHospitalHospitalUUIDAndFacilityNameIgnoreCase(
                        hospitalId,
                        dto.getFacilityName())) {
            throw new RuntimeException("Facility already exists for this hospital.");
        }
        HospitalFacilities facility = HospitalFacilitiesMapper.toEntity(dto);
        facility.setHospital(hospital);

        if (icon != null && !icon.isEmpty()) {
            String iconUrl = s3Service.uploadFile(icon, "hospital-facilities/" + hospitalId);
            facility.setIconUrl(iconUrl);
        }
        HospitalFacilities saved = hospitalFacilitiesRepository.save(facility);
        return HospitalFacilitiesMapper.toDto(saved);
    }


    @Override
    public List<HospitalFacilitiesDto> getFacilities(UUID hospitalId) {
        Hospital hospital = hospitalRepository.findByHospitalUUID(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found."));
        return hospitalFacilitiesRepository
                .findByHospitalHospitalId(hospital.getHospitalId())
                .stream()
                .map(HospitalFacilitiesMapper::toDto)
                .toList();
    }


    @Override
    public List<HospitalFacilitiesDto> getActiveFacilities(UUID hospitalId) {
        Hospital hospital = hospitalRepository.findByHospitalUUID(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found."));
        return hospitalFacilitiesRepository
                .findByHospitalHospitalIdAndIsActiveTrue(hospital.getHospitalId())
                .stream()
                .map(HospitalFacilitiesMapper::toDto)
                .toList();
    }


    @Override
    @Transactional
    public HospitalFacilitiesDto updateFacility(
            UUID facilityId,
            HospitalFacilitiesDto dto,
            MultipartFile icon) throws IOException {

        HospitalFacilities facility = hospitalFacilitiesRepository.findByHospitalFacilitiesUUID(facilityId)
                .orElseThrow(() ->
                        new RuntimeException("Facility not found."));
        UUID hospitalId = facility.getHospital().getHospitalUUID();
        if (!facility.getFacilityName()
                .equalsIgnoreCase(dto.getFacilityName())
                &&
                hospitalFacilitiesRepository
                        .existsByHospitalHospitalUUIDAndFacilityNameIgnoreCase(
                                hospitalId,
                                dto.getFacilityName()
                        )) {
            throw new RuntimeException("Facility already exists for this hospital.");
        }
        facility.setFacilityName(dto.getFacilityName());
        facility.setDescription(dto.getDescription());
        facility.setIsActive(dto.getIsActive());
        facility.setUpdatedBy(dto.getUpdatedBy());

        if (icon != null && !icon.isEmpty()) {
            if (facility.getIconUrl() != null) {
                s3Service.deleteFile(facility.getIconUrl());
            }
            String iconUrl = s3Service.uploadFile(icon, "hospital-facilities/" + hospitalId);
            facility.setIconUrl(iconUrl);
        }
        HospitalFacilities updated = hospitalFacilitiesRepository.save(facility);
        return HospitalFacilitiesMapper.toDto(updated);
    }


    @Override
    @Transactional
    public void deleteFacility(UUID facilityId) {
        HospitalFacilities facility = hospitalFacilitiesRepository.findByHospitalFacilitiesUUID(facilityId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Facility not found."
                        )
                );
        if (facility.getIconUrl() != null) {
            s3Service.deleteFile(facility.getIconUrl());
        }
        hospitalFacilitiesRepository.delete(facility);
    }
}
