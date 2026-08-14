package com.ponntrix.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Entity class for department_head table.
 *
 * This table stores the doctor assigned as the head
 * of a particular department in a hospital.
 */
@Entity
@Table(
        name = "department_head",
        schema = "ponntrix_hospital"
)
@Getter
@Setter
public class HsDepartmentHead {

    /**
     * Primary key of the department_head table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_head_id")
    private Integer departmentHeadId;

    /**
     * Unique UUID of the department head record.
     */
    @Column(
            name = "department_head_uuid",
            nullable = false,
            unique = true,
            updatable = false
    )
    private UUID departmentHeadUuid;

    /**
     * Date and time from which the assignment is effective.
     */
    @Column(name = "effective_from")
    private OffsetDateTime effectiveFrom;

    /**
     * Date and time until which the assignment is effective.
     */
    @Column(name = "effective_to")
    private OffsetDateTime effectiveTo;

    /**
     * Hospital ID.
     *
     * This column has a foreign key relationship
     * with hospital.hospital_id.
     */
    @Column(name = "hospital_id", nullable = false)
    private Integer hospitalId;

    /**
     * Doctor ID.
     *
     * This column has a foreign key relationship
     * with doctor.doctor_id.
     */
    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;

    /**
     * Department ID.
     *
     * This column has a foreign key relationship
     * with departments.departments_id.
     */
    @Column(name = "departments_id", nullable = false)
    private Integer departmentsId;

    /**
     * Generates UUID before inserting the entity.
     *
     * PostgreSQL also has a DEFAULT gen_random_uuid().
     * We generate it here so the entity always has
     * the UUID value before saving.
     */
    @PrePersist
    public void generateUuid() {

        if (departmentHeadUuid == null) {
            departmentHeadUuid = UUID.randomUUID();
        }
    }
}