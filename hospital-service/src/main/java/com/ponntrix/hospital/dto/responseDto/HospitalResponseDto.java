package com.ponntrix.hospital.dto.responseDto;

import com.ponntrix.hospital.entity.HospitalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HospitalResponseDto {

    private Integer hospitalId;

    private UUID hospitalUUID;

    private HospitalType hospitalType;

    private String hospitalName;

    private String registrationNumber;

    private String gstNumber;

    private Integer establishedYear;

    private String hospitalPhone;

    private String hospitalEmail;

    private String website;

    private String logoUrl;

    private String coverImageUrl;

    private Boolean emergencyAvailable;

    private Integer onboardingStatusId;

    private String onboardingStatusName;

    private String overView;

    private Integer organizationId;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

    private List<DocumentsResponseDto> documents;

    private AddressResponseDto address;

}