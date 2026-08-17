package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.DocumentsDto;
import com.ponntrix.hospital.entity.Documents;

public class DocumentsMapper {
    public static Documents toEntity(DocumentsDto dto) {

        Documents document = new Documents();

        document.setEntityType(dto.getEntityType());
        document.setEntityId(dto.getEntityId());
        document.setDocumentName(dto.getDocumentName());
        document.setIssuedBy(dto.getIssuedBy());
        document.setIssuedDate(dto.getIssuedDate());
        document.setExpiryDate(dto.getExpiryDate());
        document.setCreatedBy(dto.getCreatedBy());
        document.setUpdatedBy(dto.getUpdatedBy());

        return document;
    }

    public static DocumentsDto toDto(Documents documents) {

        DocumentsDto dto = new DocumentsDto();

        dto.setDocumentsUUID(documents.getDocumentsUUID());
        dto.setEntityType(documents.getEntityType());
        dto.setEntityId(documents.getEntityId());
        dto.setDocumentName(documents.getDocumentName());
        dto.setDocumentUrlLink(documents.getDocumentUrlLink());
        dto.setIssuedBy(documents.getIssuedBy());
        dto.setIssuedDate(documents.getIssuedDate());
        dto.setExpiryDate(documents.getExpiryDate());
        dto.setIsVerified(documents.getIsVerified());
        dto.setCreatedAt(documents.getCreatedAt());
        dto.setUpdatedAt(documents.getUpdatedAt());
        dto.setCreatedBy(documents.getCreatedBy());
        dto.setUpdatedBy(documents.getUpdatedBy());

        return dto;
    }
}