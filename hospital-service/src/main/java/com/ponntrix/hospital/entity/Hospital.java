package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "hospital", schema = "ponntrix_hospital")
@Getter
@Setter
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Integer hospitalId;

    @Column(name = "hospital_uuid", nullable = false, unique = true)
    private UUID hospitalUUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "hospital_type", nullable = false)
    private HospitalType hospitalType;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "established_year")
    private Integer establishedYear;

    @Column(name = "hospital_phone")
    private String hospitalPhone;

    @Column(name = "hospital_email")
    private String hospitalEmail;

    @Column(name = "website")
    private String website;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "emergency_available")
    private Boolean emergencyAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_status_id", referencedColumnName = "onboarding_status_id")
    private OnboardingStatus onboardingStatus;

    @Column(name = "over_view", columnDefinition = "TEXT")
    private String overView;

    @Column(name = "organization_id")
    private Integer organizationId;

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

        hospitalUUID = UUID.randomUUID();

        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();

        if (emergencyAvailable == null) {
            emergencyAvailable = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }

}
