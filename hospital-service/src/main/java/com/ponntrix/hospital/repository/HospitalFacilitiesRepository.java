package com.ponntrix.hospital.repository;


import com.ponntrix.hospital.entity.HospitalFacilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HospitalFacilitiesRepository extends JpaRepository<HospitalFacilities, Integer> {

    Optional<HospitalFacilities> findByHospitalFacilitiesUUID(UUID hospitalFacilitiesUUID);

    List<HospitalFacilities>
    findByHospitalHospitalId(Integer hospitalId);

    List<HospitalFacilities>
    findByHospitalHospitalIdAndIsActiveTrue(Integer hospitalId);

    boolean existsByHospitalHospitalUUIDAndFacilityNameIgnoreCase(
            UUID hospitalId,
            String facilityName
    );

    void deleteByHospitalHospitalId(Integer hospitalId);

}
