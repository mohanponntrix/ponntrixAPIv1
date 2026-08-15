package com.ponntrix.hospital.service;


import com.ponntrix.hospital.dto.SocialLinksDto;
import com.ponntrix.hospital.entity.EntityType;

import java.util.List;
import java.util.UUID;

public interface SocialLinksService {

    SocialLinksDto createSocialLink(SocialLinksDto dto);

    List<SocialLinksDto> getSocialLinks(EntityType entityType, UUID entityId);

    SocialLinksDto updateSocialLink(UUID socialLinksId, SocialLinksDto dto);

    void deleteSocialLink(UUID socialLinksId);

    void deleteEntitySocialLinks(EntityType entityType, UUID entityId);
}
