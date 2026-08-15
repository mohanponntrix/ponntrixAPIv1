package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.HospitalFacilitiesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface HospitalFacilitiesService {

    HospitalFacilitiesDto createFacility(
            UUID hospitalId,
            HospitalFacilitiesDto dto,
            MultipartFile icon
    ) throws IOException;

    List<HospitalFacilitiesDto> getFacilities(UUID hospitalId);

    List<HospitalFacilitiesDto> getActiveFacilities(UUID hospitalId);

    HospitalFacilitiesDto updateFacility(
            UUID facilityId,
            HospitalFacilitiesDto dto,
            MultipartFile icon
    ) throws IOException;

    void deleteFacility(
            UUID facilityId
    );
}
