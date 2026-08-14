package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.HsDepartmentHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for department head database operations.
 */
@Repository
public interface HsDepartmentHeadRepository
        extends JpaRepository<HsDepartmentHead, Integer> {

    /**
     * Checks whether a department head assignment
     * already exists for the same hospital, doctor
     * and department.
     *
     * This corresponds to:
     *
     * UNIQUE(departments_id, hospital_id, doctor_id)
     */
    boolean existsByDepartmentsIdAndHospitalIdAndDoctorId(
            Integer departmentsId,
            Integer hospitalId,
            Integer doctorId
    );

    /**
     * Checks duplicate assignment during update.
     *
     * The current department head ID is excluded
     * from the duplicate check.
     */
    boolean existsByDepartmentsIdAndHospitalIdAndDoctorIdAndDepartmentHeadIdNot(
            Integer departmentsId,
            Integer hospitalId,
            Integer doctorId,
            Integer departmentHeadId
    );

    /**
     * Finds a department head using its public UUID.
     */
    Optional<HsDepartmentHead> findByDepartmentHeadUuid(
            UUID departmentHeadUuid
    );
}