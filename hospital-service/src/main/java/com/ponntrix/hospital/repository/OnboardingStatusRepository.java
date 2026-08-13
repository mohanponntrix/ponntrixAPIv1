package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.OnboardingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OnboardingStatusRepository extends JpaRepository<OnboardingStatus,Integer> {
    Optional<OnboardingStatus> findByStatusName(String statusName);

    boolean existsByStatusName(String statusName);
}
