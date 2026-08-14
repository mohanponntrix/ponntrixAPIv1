package com.ponntrix.hospital.service;

import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.dto.request.DocumentsRequestDto;
import com.ponntrix.hospital.dto.response.DocumentsResponseDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentsService {

    DocumentsResponseDto uploadDocument(DocumentsRequestDto dto, MultipartFile file) throws IOException;

    List<DocumentsResponseDto> getDocumentsByType(EntityType entityType);

    List<DocumentsResponseDto> getDocument(EntityType entityType, Integer entityId);

    public List<DocumentsResponseDto> getAllDocuments();

    DocumentsResponseDto updateDocument(
            Integer documentsId,
            DocumentsRequestDto dto,
            MultipartFile file) throws IOException;

    DocumentsResponseDto verifyDocument(Integer documentsId);

    void deleteDocument(Integer documentsId);

    ResponseEntity<Resource> downloadDocument(Integer documentsId) throws IOException;

    ResponseEntity<InputStreamResource> previewDocument(Integer documentsId);
}
