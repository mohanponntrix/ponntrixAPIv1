package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    boolean existsByHospitalEmail(String hospitalEmail);

    boolean existsByHospitalPhone(String hospitalPhone);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByGstNumber(String gstNumber);

    Optional<Hospital> findByHospitalUUID(UUID hospitalUUID);

    List<Hospital> findByEmergencyAvailableTrue();

}
