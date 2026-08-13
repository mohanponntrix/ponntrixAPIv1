package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    boolean existsByCityNameIgnoreCase(String cityName);

    List<City> findByStateStateId(Integer stateId);

}
