package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.locations.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

    boolean existsByAreaNameIgnoreCase(String areaName);

    List<Area> findByCityCityId(Integer cityId);

}
