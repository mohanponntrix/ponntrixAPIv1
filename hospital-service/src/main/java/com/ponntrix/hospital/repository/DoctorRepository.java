package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    boolean existsByDoctorEmail(String doctorEmail);

    boolean existsByDoctorPhone(String doctorPhone);

    boolean existsByRegistrationNumber(String registrationNumber);

    List<Doctor> findByIsActiveTrue();
}
