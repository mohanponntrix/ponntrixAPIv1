package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "hospital_facilities", schema = "ponntrix_hospital")
@Getter
@Setter
@NoArgsConstructor
public class HospitalFacilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_facilities_id")
    private Integer hospitalFacilitiesId;

    @Column(
            name = "hospital_facilities_uuid",
            nullable = false,
            unique = true,
            updatable = false
    )
    private UUID hospitalFacilitiesUUID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(name = "facility_name", nullable = false, length = 50)
    private String facilityName;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_url", length = 100)
    private String iconUrl;

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

        hospitalFacilitiesUUID = UUID.randomUUID();

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
