package com.ponntrix.hospital.dto.responseDto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class DoctorResponseDto {

    private Integer doctorId;

    private UUID doctorUUID;

    private String firstName;

    private String lastName;

    private String gender;

    private String doctorPhone;

    private String doctorEmail;

    private Integer qualificationId;

    private String qualificationName;

    private String registrationNumber;

    private BigDecimal experienceInYears;

    private String profilePicUrl;

    private String digitalSignatureUrl;

    private Boolean isActive;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

    private List<DoctorSpecializationResponseDto> specializations;

    private List<DocumentsResponseDto> documents;

    private AddressResponseDto address;

}
