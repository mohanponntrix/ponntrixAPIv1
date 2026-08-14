package com.ponntrix.hospital.serviceimpl;

import com.ponntrix.hospital.entity.Documents;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.mapper.DocumentsMapper;
import com.ponntrix.hospital.repository.DocumentsRepository;
import com.ponntrix.hospital.dto.request.DocumentsRequestDto;
import com.ponntrix.hospital.dto.response.DocumentsResponseDto;
import com.ponntrix.hospital.service.DocumentsService;
import com.ponntrix.hospital.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentsServiceImpl implements DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final S3Service s3Service;
    private final S3Client s3Client;
    @org.springframework.beans.factory.annotation.Value("${aws.s3.bucket-name}")
    private String bucket;

    @Override
    public DocumentsResponseDto uploadDocument(
            DocumentsRequestDto dto,
            MultipartFile file) throws IOException {

        if (documentsRepository.existsByEntityTypeAndEntityIdAndDocumentName(
                dto.getEntityType(),
                dto.getEntityId(),
                dto.getDocumentName())) {

            throw new RuntimeException("Document already exists.");
        }
        Documents document = DocumentsMapper.toEntity(dto);

        String folder = dto.getEntityType().name().toLowerCase()
                + "/" + dto.getEntityId();

        String documentUrl = s3Service.uploadFile(file, folder);
        document.setDocumentUrlLink(documentUrl);
        Documents savedDocument = documentsRepository.save(document);

        return DocumentsMapper.toDto(savedDocument);
    }


    @Override
    public List<DocumentsResponseDto> getDocument(EntityType entityType, Integer entityId) {

        return documentsRepository
                .findByEntityTypeAndEntityId(entityType, entityId)
                .stream()
                .map(DocumentsMapper::toDto)
                .toList();
    }

    @Override
    public List<DocumentsResponseDto> getDocumentsByType(EntityType entityType) {

        return documentsRepository
                .findByEntityType(entityType)
                .stream()
                .map(DocumentsMapper::toDto)
                .toList();
    }

    @Override
    public List<DocumentsResponseDto> getAllDocuments() {

        return documentsRepository
                .findAll()
                .stream()
                .map(DocumentsMapper::toDto)
                .toList();
    }


    @Override
    public DocumentsResponseDto updateDocument(
            Integer documentsId,
            DocumentsRequestDto dto,
            MultipartFile file) throws IOException {

        Documents document = documentsRepository.findById(documentsId)
                .orElseThrow(() ->
                        new RuntimeException("Document not found."));

        document.setDocumentName(dto.getDocumentName());
        document.setUpdatedBy(dto.getUpdatedBy());
        document.setIssuedBy(dto.getIssuedBy());
        document.setIssuedDate(dto.getIssuedDate());
        document.setExpiryDate(dto.getExpiryDate());

        if (file != null && !file.isEmpty()) {

            // Delete old file from S3
            s3Service.deleteFile(document.getDocumentUrlLink());

            String folder = document.getEntityType().name().toLowerCase()
                    + "/" + document.getEntityId();

            String documentUrl = s3Service.uploadFile(file, folder);

            document.setDocumentUrlLink(documentUrl);
        }
        Documents updatedDocument = documentsRepository.save(document);

        return DocumentsMapper.toDto(updatedDocument);
    }


    @Override
    public DocumentsResponseDto verifyDocument(Integer documentsId) {

        Documents document =
                documentsRepository.findById(documentsId)
                        .orElseThrow(() ->
                                new RuntimeException("Document not found."));

        document.setIsVerified(true);

        return DocumentsMapper.toDto(
                documentsRepository.save(document));
    }


    @Override
    public void deleteDocument(Integer documentsId) {
        Documents document = documentsRepository.findById(documentsId)
                        .orElseThrow(() -> new RuntimeException("Document not found."));
        s3Service.deleteFile(document.getDocumentUrlLink());
        documentsRepository.delete(document);
    }


    @Override
    public ResponseEntity<Resource> downloadDocument(Integer documentsId) throws IOException {

        Documents document = documentsRepository.findById(documentsId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        String fileUrl = document.getDocumentUrlLink();
        String key = fileUrl.substring(fileUrl.indexOf(".com/") + 5);

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(request);

        String contentType = inputStream.response().contentType();

        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + document.getDocumentName() + "\"")
                .body(new InputStreamResource(inputStream));
    }


    @Override
    public ResponseEntity<InputStreamResource> previewDocument(Integer documentsId) {

        Documents document = documentsRepository.findById(documentsId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        String fileUrl = document.getDocumentUrlLink();

        String key = fileUrl.substring(fileUrl.indexOf(".com/") + 5);

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> inputStream =
                s3Client.getObject(request);

        String contentType = inputStream.response().contentType();

        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(new InputStreamResource(inputStream));
    }
}
