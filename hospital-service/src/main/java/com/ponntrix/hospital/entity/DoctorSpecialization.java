package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "doctor_specializations",
        schema = "ponntrix_hospital",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "doctor_specialization_unique",
                        columnNames = {
                                "doctor_id",
                                "specialization_id"
                        })
        })
@Getter
@Setter
@NoArgsConstructor
public class DoctorSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_specializations_id")
    private Integer doctorSpecializationsId;

    @Column(
            name = "doctor_specializations_uuid",
            nullable = false,
            unique = true)
    private UUID doctorSpecializationsUUID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization_id", referencedColumnName = "specialization_id")
    private Specialization specialization;

    @Column(name = "is_primary")
    private Boolean isPrimary;

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

        doctorSpecializationsUUID = UUID.randomUUID();

        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();

        if (isPrimary == null) {
            isPrimary = false;
        }
    }

    @PreUpdate
    public void preUpdate() {

        updatedAt = OffsetDateTime.now();

    }

}