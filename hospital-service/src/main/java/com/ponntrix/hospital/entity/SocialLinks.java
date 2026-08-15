package com.ponntrix.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "social_links",
        schema = "ponntrix_hospital",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "social_links_entity_media_unique",
                        columnNames = {
                                "entity_type",
                                "entity_id",
                                "social_media_name"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class SocialLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_links_id")
    private Integer socialLinksId;

    @Column(name = "social_links_uuid", nullable = false, unique = true, updatable = false)
    private UUID socialLinksUUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id", nullable = false, columnDefinition = "uuid")
    private UUID entityId;

    @Column(name = "social_media_name", nullable = false, length = 30)
    private String socialMediaName;

    @Column(name = "social_media_link", nullable = false, length = 20)
    private String socialMediaLink;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;


    @PrePersist
    public void prePersist() {

        socialLinksUUID = UUID.randomUUID();

        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }


    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
