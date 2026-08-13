package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.Address;
import com.ponntrix.hospital.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Optional<Address> findByEntityTypeAndEntityId(
            EntityType entityType,
            Integer entityId);

    List<Address> findAllByEntityType(
            EntityType entityType);

    Optional<Address> findByEntityTypeAndEntityIdAndIsPrimaryTrue(
            EntityType entityType,
            Integer entityId);

    boolean existsByEntityTypeAndEntityId(
            EntityType entityType,
            Integer entityId);

    void deleteByEntityTypeAndEntityId(EntityType entityType, Integer doctorId);
}
