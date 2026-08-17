package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.Documents;
import com.ponntrix.hospital.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents,Integer> {

    Optional<Documents> findByDocumentsUUID(UUID documentsUUID);

    boolean existsByEntityTypeAndEntityIdAndDocumentName(
            EntityType entityType,
            UUID entityId,
            String documentName);

    List<Documents> findByEntityTypeAndEntityId(EntityType entityType, UUID entityId);

    List<Documents> findByEntityType(EntityType entityType);
}
