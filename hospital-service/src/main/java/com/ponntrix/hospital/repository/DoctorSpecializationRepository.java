package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorSpecializationRepository
        extends JpaRepository<DoctorSpecialization, Integer> {

    List<DoctorSpecialization> findByDoctorDoctorId(Integer doctorId);

    Optional<DoctorSpecialization>
    findByDoctorDoctorIdAndIsPrimaryTrue(Integer doctorId);

    boolean existsByDoctorDoctorIdAndSpecializationSpecializationId(
            Integer doctorId,
            Integer specializationId);

    Optional<DoctorSpecialization>
    findByDoctorDoctorIdAndSpecializationSpecializationId(
            Integer doctorId,
            Integer specializationId);

    void deleteByDoctorDoctorIdAndSpecializationSpecializationId(
            Integer doctorId,
            Integer specializationId);

    void deleteByDoctorDoctorId(Integer doctorId);
}
