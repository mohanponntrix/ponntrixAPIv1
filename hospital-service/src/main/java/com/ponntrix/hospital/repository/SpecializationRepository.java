package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    Optional<Specialization> findBySpecializationNameIgnoreCase(String specializationName);

    boolean existsBySpecializationNameIgnoreCase(String specializationName);


}
