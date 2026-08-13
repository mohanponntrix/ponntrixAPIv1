package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification,Integer> {

    boolean existsByQualificationNameIgnoreCase(String qualificationName);
}
