package com.ponntrix.hospital.repository;

import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.SocialLinks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SocialLinksRepository extends JpaRepository<SocialLinks, Integer> {

    Optional<SocialLinks> findBySocialLinksUUID(UUID socialLinksUUID);

    List<SocialLinks> findByEntityTypeAndEntityId(
            EntityType entityType,
            UUID entityId
    );

    Optional<SocialLinks>
    findByEntityTypeAndEntityIdAndSocialMediaNameIgnoreCase(
            EntityType entityType,
            UUID entityId,
            String socialMediaName
    );

    boolean existsByEntityTypeAndEntityIdAndSocialMediaNameIgnoreCase(
            EntityType entityType,
            UUID entityId,
            String socialMediaName
    );

    void deleteByEntityTypeAndEntityId(
            EntityType entityType,
            UUID entityId
    );
}
