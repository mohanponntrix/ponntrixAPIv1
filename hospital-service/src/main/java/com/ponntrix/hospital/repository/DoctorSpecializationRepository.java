package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long> {

    Optional<DoctorSpecialization> findByDoctorSpecializationsUUID(UUID doctorSpecializationsUUID);

    List<DoctorSpecialization> findByDoctorDoctorUUID(UUID doctorId);

    Optional<DoctorSpecialization>
    findByDoctorDoctorUUIDAndIsPrimaryTrue(UUID doctorId);

    boolean existsByDoctorDoctorUUIDAndSpecializationSpecializationId(
            UUID doctorId,
            Integer specializationId);

    Optional<DoctorSpecialization>
    findByDoctorDoctorIdAndSpecializationSpecializationId(
            Integer doctorId,
            Integer specializationId);

    void deleteByDoctorDoctorIdAndSpecializationSpecializationId(
            Integer doctorId,
            Integer specializationId);

    void deleteByDoctorDoctorUUID(UUID doctorId);
}
