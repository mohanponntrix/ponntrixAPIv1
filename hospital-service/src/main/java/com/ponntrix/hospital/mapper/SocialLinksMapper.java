package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.SocialLinksDto;
import com.ponntrix.hospital.entity.SocialLinks;

public class SocialLinksMapper {

    public static SocialLinks toEntity(SocialLinksDto dto) {

        SocialLinks entity = new SocialLinks();
        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId());
        entity.setSocialMediaName(dto.getSocialMediaName());
        entity.setSocialMediaLink(dto.getSocialMediaLink());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        return entity;
    }

    public static SocialLinksDto toDto(SocialLinks entity) {

        SocialLinksDto dto = new SocialLinksDto();

        dto.setSocialLinksUUID(entity.getSocialLinksUUID());
        dto.setEntityType(entity.getEntityType());
        dto.setEntityId(entity.getEntityId());
        dto.setSocialMediaName(entity.getSocialMediaName());
        dto.setSocialMediaLink(entity.getSocialMediaLink());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }
}
