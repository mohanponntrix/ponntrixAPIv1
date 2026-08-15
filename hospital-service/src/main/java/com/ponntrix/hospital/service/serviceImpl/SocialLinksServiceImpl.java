package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.dto.SocialLinksDto;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.SocialLinks;
import com.ponntrix.hospital.mapper.SocialLinksMapper;
import com.ponntrix.hospital.repository.SocialLinksRepository;
import com.ponntrix.hospital.service.SocialLinksService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialLinksServiceImpl implements SocialLinksService {

    private final SocialLinksRepository socialLinksRepository;


    @Override
    @Transactional
    public SocialLinksDto
    createSocialLink(SocialLinksDto dto) {

        if (dto.getEntityType() == null || dto.getEntityId() == null) {
            throw new RuntimeException("Entity type and entity ID are required.");
        }
        if (dto.getSocialMediaName() == null || dto.getSocialMediaName().isBlank()) {
            throw new RuntimeException("Social media name is required.");
        }
        if (dto.getSocialMediaLink() == null || dto.getSocialMediaLink().isBlank()) {
            throw new RuntimeException("Social media link is required.");
        }
        if (socialLinksRepository.existsByEntityTypeAndEntityIdAndSocialMediaNameIgnoreCase(
                        dto.getEntityType(),
                        dto.getEntityId(),
                        dto.getSocialMediaName())) {
            throw new RuntimeException("Social media link already exists.");
        }
        SocialLinks entity = SocialLinksMapper.toEntity(dto);
        SocialLinks saved = socialLinksRepository.save(entity);
        return SocialLinksMapper.toDto(saved);
    }


    @Override
    public List<SocialLinksDto>
    getSocialLinks(EntityType entityType, UUID entityId) {
        return socialLinksRepository
                .findByEntityTypeAndEntityId(
                        entityType,
                        entityId
                )
                .stream()
                .map(SocialLinksMapper::toDto)
                .toList();
    }


    @Override
    @Transactional
    public SocialLinksDto
    updateSocialLink(UUID socialLinksId, SocialLinksDto dto) {
        SocialLinks entity = socialLinksRepository.findBySocialLinksUUID(socialLinksId)
                .orElseThrow(() -> new RuntimeException("Social link not found."));

        socialLinksRepository
                .findByEntityTypeAndEntityIdAndSocialMediaNameIgnoreCase(
                        dto.getEntityType(), dto.getEntityId(), dto.getSocialMediaName())
                .ifPresent(existing -> {

                    if (!existing.getSocialLinksUUID().equals(socialLinksId)) {
                        throw new RuntimeException("Social media link already exists.");}
                });
        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId());
        entity.setSocialMediaName(dto.getSocialMediaName());
        entity.setSocialMediaLink(dto.getSocialMediaLink());
        entity.setUpdatedBy(dto.getUpdatedBy());

        SocialLinks updated = socialLinksRepository.save(entity);
        return SocialLinksMapper.toDto(updated);
    }


    @Override
    @Transactional
    public void
    deleteSocialLink(UUID socialLinksId) {
        SocialLinks entity = socialLinksRepository.findBySocialLinksUUID(socialLinksId)
                .orElseThrow(() ->
                        new RuntimeException("Social link not found."));
        socialLinksRepository.delete(entity);
    }


    @Override
    @Transactional
    public void
    deleteEntitySocialLinks(EntityType entityType, UUID entityId) {
        socialLinksRepository.deleteByEntityTypeAndEntityId(entityType, entityId);
    }
}
