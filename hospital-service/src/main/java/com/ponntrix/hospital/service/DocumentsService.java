package com.ponntrix.hospital.service;

import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.dto.DocumentsDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DocumentsService {

    DocumentsDto uploadDocument(DocumentsDto dto, MultipartFile file) throws IOException;

    List<DocumentsDto> getDocumentsByType(EntityType entityType);

    List<DocumentsDto> getDocument(EntityType entityType, UUID entityId);

    public List<DocumentsDto> getAllDocuments();

    DocumentsDto updateDocument(
            UUID documentsId,
            DocumentsDto dto,
            MultipartFile file) throws IOException;

    DocumentsDto verifyDocument(UUID documentsId);

    void deleteDocument(UUID documentsId);

    ResponseEntity<Resource> downloadDocument(UUID documentsId) throws IOException;

    ResponseEntity<InputStreamResource> previewDocument(UUID documentsId);
}
