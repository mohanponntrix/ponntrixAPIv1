package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperatingHoursRepository extends JpaRepository<OperatingHours, Integer> {

    Optional<OperatingHours> findByOperatingHoursUuid(UUID operatingHoursUuid);

    List<OperatingHours>
    findByEntityTypeAndEntityIdOrderByDayOfWeekDayOfWeekId(EntityType entityType, UUID entityId);


    boolean existsByEntityTypeAndEntityIdAndDayOfWeekDayOfWeekId(
            EntityType entityType,
            UUID entityId,
            Integer dayOfWeekId
    );

    void deleteByEntityTypeAndEntityId(
            EntityType entityType,
            UUID entityId
    );
}
