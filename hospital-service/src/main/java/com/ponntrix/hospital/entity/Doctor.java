package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "doctor", schema = "ponntrix_hospital")
@Getter
@Setter
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "doctor_uuid", nullable = false, unique = true)
    private UUID doctorUUID;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "doctor_phone", nullable = false, unique = true)
    private String doctorPhone;

    @Column(name = "doctor_email", nullable = false, unique = true)
    private String doctorEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id", referencedColumnName = "qualification_id")
    private Qualification qualification;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "experience_in_years", nullable = false)
    private BigDecimal experienceInYears;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    @Column(name = "digital_signature_url")
    private String digitalSignatureUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @PrePersist
    public void prePersist() {
        doctorUUID = UUID.randomUUID();
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();

        if (isActive == null) {
            isActive = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
