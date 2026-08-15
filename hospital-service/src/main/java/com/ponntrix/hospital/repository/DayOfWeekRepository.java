package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Integer> {

    Optional<DayOfWeek> findByDayNameIgnoreCase(String dayName);

    List<DayOfWeek> findAllByOrderByDayOfWeekIdAsc();
}