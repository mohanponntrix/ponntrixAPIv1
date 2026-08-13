package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.entity.*;
import com.ponntrix.hospital.mapper.AddressMapper;
import com.ponntrix.hospital.mapper.DocumentsMapper;
import com.ponntrix.hospital.mapper.HospitalMapper;
import com.ponntrix.hospital.repository.AddressRepository;
import com.ponntrix.hospital.repository.DocumentsRepository;
import com.ponntrix.hospital.repository.HospitalRepository;
import com.ponntrix.hospital.repository.OnboardingStatusRepository;
import com.ponntrix.hospital.dto.requestDto.AddressRequestDto;
import com.ponntrix.hospital.dto.requestDto.DocumentsRequestDto;
import com.ponntrix.hospital.dto.requestDto.HospitalRequestDto;
import com.ponntrix.hospital.dto.responseDto.DocumentsResponseDto;
import com.ponntrix.hospital.dto.responseDto.HospitalResponseDto;
import com.ponntrix.hospital.service.AddressService;
import com.ponntrix.hospital.service.DocumentsService;
import com.ponntrix.hospital.service.HospitalService;
import com.ponntrix.hospital.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final OnboardingStatusRepository onboardingStatusRepository;
    private final DocumentsRepository documentsRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;
    private final DocumentsService documentsService;
    private final S3Service s3Service;

    @Transactional
    @Override
    public HospitalResponseDto createHospital(
            HospitalRequestDto dto,
            MultipartFile logo,
            MultipartFile coverImage,
            List<MultipartFile> documentFiles) throws IOException {

        if (dto.getHospitalEmail() != null &&
                hospitalRepository.existsByHospitalEmail(dto.getHospitalEmail())) {
            throw new RuntimeException("Hospital email already exists.");
        }

        if (dto.getHospitalPhone() != null &&
                hospitalRepository.existsByHospitalPhone(dto.getHospitalPhone())) {
            throw new RuntimeException("Hospital phone already exists.");
        }

        if (dto.getRegistrationNumber() != null &&
                hospitalRepository.existsByRegistrationNumber(dto.getRegistrationNumber())) {
            throw new RuntimeException("Registration number already exists.");
        }

        if (dto.getGstNumber() != null &&
                hospitalRepository.existsByGstNumber(dto.getGstNumber())) {
            throw new RuntimeException("GST number already exists.");
        }

        Hospital hospital = HospitalMapper.toEntity(dto);
        Hospital savedHospital = hospitalRepository.save(hospital);

        OnboardingStatus status = onboardingStatusRepository
                .findById(dto.getOnboardingStatusId())
                .orElseThrow(() -> new RuntimeException("Onboarding status not found"));
        hospital.setOnboardingStatus(status);

        if (logo != null && !logo.isEmpty()) {
            String logoUrl = s3Service.uploadFile(logo, "hospital-logos");
            hospital.setLogoUrl(logoUrl);
        }
        if (coverImage != null && !coverImage.isEmpty()) {
            String coverUrl = s3Service.uploadFile(coverImage, "hospital-cover-images");
            hospital.setCoverImageUrl(coverUrl);
        }

        if (dto.getAddress() != null) {

            AddressRequestDto address = dto.getAddress();

            address.setEntityType(EntityType.HOSPITAL);
            address.setEntityId(savedHospital.getHospitalId());

            addressService.createAddress(address);
        }

        if (dto.getDocuments() != null && !dto.getDocuments().isEmpty()) {

            if (documentFiles == null || documentFiles.size() != dto.getDocuments().size()) {
                throw new IllegalArgumentException(
                        "Each document metadata entry must have a corresponding document file.");
            }

            for (int i = 0; i < dto.getDocuments().size(); i++) {

                DocumentsRequestDto document = dto.getDocuments().get(i);
                MultipartFile file = documentFiles.get(i);

                if (document.getDocumentName() == null || document.getDocumentName().isBlank()) {
                    document.setDocumentName(file.getOriginalFilename());
                }

                if (document.getDocumentName() == null || document.getDocumentName().isBlank()) {
                    throw new IllegalArgumentException("Document name is required.");
                }

                document.setEntityType(EntityType.HOSPITAL);
                document.setEntityId(savedHospital.getHospitalId());

                documentsService.uploadDocument(document, file);
            }
        }


        return buildHospitalResponse(savedHospital);
    }


    @Override
    public List<HospitalResponseDto> getAllHospitals() {

        return hospitalRepository.findAll()
                .stream()
                .map(this::buildHospitalResponse)
                .toList();
    }

    @Override
    public HospitalResponseDto getHospitalById(Integer hospitalId) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found."));

        return buildHospitalResponse(hospital);
    }

    @Override
    @Transactional
    public HospitalResponseDto updateHospital(
            Integer hospitalId,
            HospitalRequestDto dto,
            MultipartFile logo,
            MultipartFile coverImage,
            List<MultipartFile> documentFiles) throws IOException {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found."));

        hospital.setHospitalType(dto.getHospitalType());
        hospital.setHospitalName(dto.getHospitalName());
        hospital.setRegistrationNumber(dto.getRegistrationNumber());
        hospital.setGstNumber(dto.getGstNumber());
        hospital.setEstablishedYear(dto.getEstablishedYear());
        hospital.setHospitalPhone(dto.getHospitalPhone());
        hospital.setHospitalEmail(dto.getHospitalEmail());
        hospital.setWebsite(dto.getWebsite());
        hospital.setEmergencyAvailable(dto.getEmergencyAvailable());
        hospital.setOverView(dto.getOverView());
        hospital.setOrganizationId(dto.getOrganizationId());
        hospital.setUpdatedBy(dto.getUpdatedBy());

        OnboardingStatus status =
                onboardingStatusRepository
                        .findById(dto.getOnboardingStatusId())
                        .orElseThrow(() -> new RuntimeException("Onboarding status not found"));

        hospital.setOnboardingStatus(status);

        if (logo != null && !logo.isEmpty()) {

            String logoUrl = s3Service.uploadFile(logo, "hospital-logos");

            hospital.setLogoUrl(logoUrl);
        }

        if (coverImage != null && !coverImage.isEmpty()) {

            String coverUrl = s3Service.uploadFile(coverImage, "hospital-cover-images");

            hospital.setCoverImageUrl(coverUrl);

        }

        if (dto.getAddress() != null) {

            AddressRequestDto addressDto = dto.getAddress();

            addressDto.setEntityType(EntityType.HOSPITAL);

            addressDto.setEntityId(hospitalId);

            addressService.updateAddressByEntity(
                    EntityType.HOSPITAL,
                    hospitalId,
                    addressDto
            );
        }

        if (dto.getDocuments() != null && !dto.getDocuments().isEmpty()) {

            if (documentFiles == null) {documentFiles = List.of();
            }

            for (int i = 0; i < dto.getDocuments().size(); i++) {

                DocumentsRequestDto documentDto = dto.getDocuments().get(i);

                documentDto.setEntityType(EntityType.HOSPITAL);

                documentDto.setEntityId(hospitalId);

                MultipartFile file = null;

                if (i < documentFiles.size()) {
                    file = documentFiles.get(i);
                }

                // Existing document
                if (documentDto.getDocumentsId() != null) {

                    documentsService.updateDocument(
                            documentDto.getDocumentsId(),
                            documentDto,
                            file
                    );

                }
                // New document
                else {if (file == null || file.isEmpty()) {
                        throw new RuntimeException("Each new document must have a file.");
                    }
                    documentsService.uploadDocument(documentDto, file);
                }
            }

        }


        Hospital updatedHospital = hospitalRepository.save(hospital);

        return buildHospitalResponse(updatedHospital);
    }


    @Override
    public DocumentsResponseDto uploadHospitalDocument(
            Integer hospitalId,
            DocumentsRequestDto dto,
            MultipartFile file) throws IOException {

        hospitalRepository.findById(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hospital not found."));

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Document file is required.");
        }
        dto.setEntityType(EntityType.HOSPITAL);
        dto.setEntityId(hospitalId);
        return documentsService.uploadDocument(dto, file);
    }


    @Override
    @Transactional
    public void deleteHospital(Integer hospitalId) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found."));
        // 1. Find and delete documents
        List<Documents> documents =
                documentsRepository.findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospitalId
                );

        for (Documents document : documents) {
            // Delete document file from S3
            if (document.getDocumentUrlLink() != null &&
                    !document.getDocumentUrlLink().isBlank()) {

                s3Service.deleteFile(
                        document.getDocumentUrlLink()
                );
            }
            // Delete document database record
            documentsRepository.delete(document);
        }

        // 2. Delete address
        addressRepository
                .findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospitalId
                )
                .ifPresent(addressRepository::delete);

        hospitalRepository.delete(hospital);
    }

    private HospitalResponseDto buildHospitalResponse(Hospital hospital) {

        HospitalResponseDto response = HospitalMapper.toDto(hospital);

        // Documents
        List<DocumentsResponseDto> documents = documentsRepository
                .findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospital.getHospitalId())
                .stream()
                .map(DocumentsMapper::toDto)
                .toList();

        response.setDocuments(documents);

        // Address
        addressRepository
                .findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospital.getHospitalId())
                .ifPresent(address ->
                        response.setAddress(
                                AddressMapper.toDto(address)));

        return response;
    }
}
