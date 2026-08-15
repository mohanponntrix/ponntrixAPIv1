package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.dto.DocumentsDto;
import com.ponntrix.hospital.service.DocumentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentsController {

    private final DocumentsService documentsService;

    // Upload Document
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentsDto> uploadDocument(
            @ModelAttribute DocumentsDto dto,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(documentsService.uploadDocument(dto, file));
    }

    // Get Document
    @GetMapping("/{entityType}/{entityId}")
    public ResponseEntity<List<DocumentsDto>> getDocument(
            @PathVariable EntityType entityType,
            @PathVariable UUID entityId) {
        return ResponseEntity.ok(documentsService.getDocument(entityType, entityId));
    }

    // Get Documents
    @GetMapping("/{entityType}")
    public ResponseEntity<List<DocumentsDto>> getDocumentsByType(
            @PathVariable EntityType entityType){
        return ResponseEntity.ok(documentsService.getDocumentsByType(entityType));
    }

    @GetMapping
    public List<DocumentsDto> getAllDocuments(){
        return (documentsService.getAllDocuments());
    }

    // Update Document
    @PutMapping(value = "/update/{documentsId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentsDto> updateDocument(
            @PathVariable UUID documentsId,
            @ModelAttribute DocumentsDto dto,
            @RequestPart(value = "file", required = false)
            MultipartFile file) throws IOException {
        return ResponseEntity.ok(documentsService.updateDocument(documentsId, dto, file));
    }

    // Verify Document
    @PutMapping("/verify/{documentsId}")
    public ResponseEntity<DocumentsDto> verifyDocument(@PathVariable UUID documentsId) {
        return ResponseEntity.ok(documentsService.verifyDocument(documentsId));
    }

    // Delete Document
    @DeleteMapping("/{documentsId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID documentsId) {
        documentsService.deleteDocument(documentsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{documentsId}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable UUID documentsId) throws IOException {
        return documentsService.downloadDocument(documentsId);
    }

    @GetMapping("/preview/{documentsId}")
    public ResponseEntity<InputStreamResource> previewDocument(
            @PathVariable UUID documentsId) {

        return documentsService.previewDocument(documentsId);
    }
}
