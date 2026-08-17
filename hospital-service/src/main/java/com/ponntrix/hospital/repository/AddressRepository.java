package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.locations.Address;
import com.ponntrix.hospital.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Optional<Address> findByAddressUUID(UUID addressUUID);

    Optional<Address> findByEntityTypeAndEntityId(
            EntityType entityType,
            UUID entityId);

    List<Address> findAllByEntityType(
            EntityType entityType);

    Optional<Address> findByEntityTypeAndEntityIdAndIsPrimaryTrue(
            EntityType entityType,
            UUID entityId);

    boolean existsByEntityTypeAndEntityId(
            EntityType entityType,
            UUID entityId);

    void deleteByEntityTypeAndEntityId(EntityType entityType, UUID entityId);

}
