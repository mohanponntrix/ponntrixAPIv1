package com.ponntrix.hospital.dto.requestDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DoctorRequestDto {

    private String firstName;

    private String lastName;

    private String gender;

    private String doctorPhone;

    private String doctorEmail;

    private Integer qualificationId;

    private String registrationNumber;

    private BigDecimal experienceInYears;

    private Integer createdBy;

    private Integer updatedBy;

}
