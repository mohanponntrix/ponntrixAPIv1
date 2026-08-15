package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.locations.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    boolean existsByCountryNameIgnoreCase(String countryName);

}
