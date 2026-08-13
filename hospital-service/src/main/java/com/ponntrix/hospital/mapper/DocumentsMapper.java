package com.ponntrix.hospital.mapper;


import com.ponntrix.hospital.dto.requestDto.DocumentsRequestDto;
import com.ponntrix.hospital.dto.responseDto.DocumentsResponseDto;
import com.ponntrix.hospital.entity.Documents;

public class DocumentsMapper {
    public static Documents toEntity(DocumentsRequestDto dto) {

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

    public static DocumentsResponseDto toDto(Documents documents) {

        DocumentsResponseDto dto = new DocumentsResponseDto();

        dto.setDocumentsId(documents.getDocumentsId());
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