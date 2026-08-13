package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.Documents;
import com.ponntrix.hospital.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents,Integer> {

    boolean existsByEntityTypeAndEntityIdAndDocumentName(
            EntityType entityType,
            Integer entityId,
            String documentName);

    List<Documents> findByEntityTypeAndEntityId(EntityType entityType, Integer entityId);

    List<Documents> findByEntityType(EntityType entityType);
}
