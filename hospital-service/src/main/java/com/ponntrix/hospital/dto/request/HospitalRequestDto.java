package com.ponntrix.hospital.dto.request;

import com.ponntrix.hospital.dto.DocumentsDto;
import com.ponntrix.hospital.entity.HospitalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HospitalRequestDto {

    private HospitalType hospitalType;

    private String hospitalName;

    private String registrationNumber;

    private String gstNumber;

    private Integer establishedYear;

    private String hospitalPhone;

    private String hospitalEmail;

    private String website;

    private Boolean emergencyAvailable;

    private Integer onboardingStatusId;

    private Integer organizationId;

    private String overView;

    private Integer createdBy;

    private Integer updatedBy;

    private AddressRequestDto address;

    private List<DocumentsDto> documents;

}