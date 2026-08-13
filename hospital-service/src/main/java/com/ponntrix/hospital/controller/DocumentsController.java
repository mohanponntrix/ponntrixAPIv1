package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.dto.requestDto.DocumentsRequestDto;
import com.ponntrix.hospital.dto.responseDto.DocumentsResponseDto;
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

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentsController {

    private final DocumentsService documentsService;

    // Upload Document
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentsResponseDto> uploadDocument(
            @ModelAttribute DocumentsRequestDto dto,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(documentsService.uploadDocument(dto, file));
    }

    // Get Document
    @GetMapping("/{entityType}/{entityId}")
    public ResponseEntity<List<DocumentsResponseDto>> getDocument(
            @PathVariable EntityType entityType,
            @PathVariable Integer entityId) {
        return ResponseEntity.ok(documentsService.getDocument(entityType, entityId));
    }

    // Get Documents
    @GetMapping("/{entityType}")
    public ResponseEntity<List<DocumentsResponseDto>> getDocumentsByType(
            @PathVariable EntityType entityType){
        return ResponseEntity.ok(documentsService.getDocumentsByType(entityType));
    }

    @GetMapping
    public List<DocumentsResponseDto> getAllDocuments(){
        return (documentsService.getAllDocuments());
    }

    // Update Document
    @PutMapping(value = "/update/{documentsId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentsResponseDto> updateDocument(
            @PathVariable Integer documentsId,
            @ModelAttribute DocumentsRequestDto dto,
            @RequestPart(value = "file", required = false)
            MultipartFile file) throws IOException {
        return ResponseEntity.ok(documentsService.updateDocument(documentsId, dto, file));
    }

    // Verify Document
    @PutMapping("/verify/{documentsId}")
    public ResponseEntity<DocumentsResponseDto> verifyDocument(@PathVariable Integer documentsId) {
        return ResponseEntity.ok(documentsService.verifyDocument(documentsId));
    }

    // Delete Document
    @DeleteMapping("/{documentsId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Integer documentsId) {
        documentsService.deleteDocument(documentsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{documentsId}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Integer documentsId) throws IOException {
        return documentsService.downloadDocument(documentsId);
    }

    @GetMapping("/preview/{documentsId}")
    public ResponseEntity<InputStreamResource> previewDocument(
            @PathVariable Integer documentsId) {

        return documentsService.previewDocument(documentsId);
    }
}