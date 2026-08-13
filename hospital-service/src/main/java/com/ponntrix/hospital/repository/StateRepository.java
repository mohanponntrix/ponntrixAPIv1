package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

    boolean existsByStateNameIgnoreCase(String stateName);

    List<State> findByCountry_CountryId(Integer countryId);
}
