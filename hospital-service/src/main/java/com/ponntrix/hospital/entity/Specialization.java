package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "specialization",
        schema = "ponntrix_hospital",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "specialization_name_unique",
                        columnNames = "specialization_name")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialization_id")
    private Integer specializationId;

    @Column(name = "specialization_name", nullable = false)
    private String specializationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id",referencedColumnName = "qualification_id")
    private Qualification qualification;

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

        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();

    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}