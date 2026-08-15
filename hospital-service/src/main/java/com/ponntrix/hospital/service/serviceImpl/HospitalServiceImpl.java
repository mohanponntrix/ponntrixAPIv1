package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.dto.HospitalFacilitiesDto;
import com.ponntrix.hospital.dto.SocialLinksDto;
import com.ponntrix.hospital.entity.Documents;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.Hospital;
import com.ponntrix.hospital.entity.OnboardingStatus;
import com.ponntrix.hospital.mapper.*;
import com.ponntrix.hospital.repository.*;
import com.ponntrix.hospital.dto.request.AddressRequestDto;
import com.ponntrix.hospital.dto.DocumentsDto;
import com.ponntrix.hospital.dto.request.HospitalRequestDto;
import com.ponntrix.hospital.dto.response.HospitalResponseDto;
import com.ponntrix.hospital.service.DocumentsService;
import com.ponntrix.hospital.service.HospitalService;
import com.ponntrix.hospital.service.S3Service.S3Service;
import com.ponntrix.hospital.service.locationService.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final OnboardingStatusRepository onboardingStatusRepository;
    private final DocumentsRepository documentsRepository;
    private final AddressRepository addressRepository;
    private final HospitalFacilitiesRepository facilitiesRepository;
    private final SocialLinksRepository socialLinksRepository;
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
            address.setEntityId(savedHospital.getHospitalUUID());

            addressService.createAddress(address);
        }

        if (dto.getDocuments() != null && !dto.getDocuments().isEmpty()) {

            if (documentFiles == null || documentFiles.size() != dto.getDocuments().size()) {
                throw new IllegalArgumentException(
                        "Each document metadata entry must have a corresponding document file.");
            }

            for (int i = 0; i < dto.getDocuments().size(); i++) {

                DocumentsDto document = dto.getDocuments().get(i);
                MultipartFile file = documentFiles.get(i);

                if (document.getDocumentName() == null || document.getDocumentName().isBlank()) {
                    document.setDocumentName(file.getOriginalFilename());
                }

                if (document.getDocumentName() == null || document.getDocumentName().isBlank()) {
                    throw new IllegalArgumentException("Document name is required.");
                }

                document.setEntityType(EntityType.HOSPITAL);
                document.setEntityId(savedHospital.getHospitalUUID());

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
    public HospitalResponseDto getHospitalById(UUID hospitalUUID) {

        Hospital hospital = hospitalRepository.findByHospitalUUID(hospitalUUID)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found."));

        return buildHospitalResponse(hospital);
    }

    @Override
    @Transactional
    public HospitalResponseDto updateHospital(
            UUID hospitalId,
            HospitalRequestDto dto,
            MultipartFile logo,
            MultipartFile coverImage,
            List<MultipartFile> documentFiles) throws IOException {

        Hospital hospital = hospitalRepository.findByHospitalUUID(hospitalId)
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

            addressDto.setEntityId(hospital.getHospitalUUID());

            addressService.updateAddressByEntity(
                    EntityType.HOSPITAL,
                    hospital.getHospitalUUID(),
                    addressDto
            );
        }

        if (dto.getDocuments() != null && !dto.getDocuments().isEmpty()) {

            if (documentFiles == null) {documentFiles = List.of();
            }

            for (int i = 0; i < dto.getDocuments().size(); i++) {

                DocumentsDto documentDto = dto.getDocuments().get(i);

                documentDto.setEntityType(EntityType.HOSPITAL);

                documentDto.setEntityId(hospital.getHospitalUUID());

                MultipartFile file = null;

                if (i < documentFiles.size()) {
                    file = documentFiles.get(i);
                }

                // Existing document
                if (documentDto.getDocumentsUUID() != null) {

                    documentsService.updateDocument(
                            documentDto.getDocumentsUUID(),
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
    public DocumentsDto uploadHospitalDocument(
            UUID hospitalId,
            DocumentsDto dto,
            MultipartFile file) throws IOException {

        Hospital hospital = hospitalRepository.findByHospitalUUID(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hospital not found."));

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Document file is required.");
        }
        dto.setEntityType(EntityType.HOSPITAL);
        dto.setEntityId(hospital.getHospitalUUID());
        return documentsService.uploadDocument(dto, file);
    }


    @Override
    @Transactional
    public void deleteHospital(UUID hospitalId) {

        Hospital hospital = hospitalRepository.findByHospitalUUID(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found."));
        // 1. Find and delete documents
        List<Documents> documents =
                documentsRepository.findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospital.getHospitalUUID()
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
                        hospital.getHospitalUUID()
                )
                .ifPresent(addressRepository::delete);

        hospitalRepository.delete(hospital);
    }

    private HospitalResponseDto buildHospitalResponse(Hospital hospital) {

        HospitalResponseDto response = HospitalMapper.toDto(hospital);

        // Documents
        List<DocumentsDto> documents = documentsRepository
                .findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospital.getHospitalUUID())
                .stream()
                .map(DocumentsMapper::toDto)
                .toList();

        response.setDocuments(documents);

        // Facilities
        List<HospitalFacilitiesDto> facilities = facilitiesRepository
                .findByHospitalHospitalId(hospital.getHospitalId())
                .stream()
                .map(HospitalFacilitiesMapper::toDto)
                .toList();

        response.setFacilities(facilities);

        // Social Links
        List<SocialLinksDto> socialLinks = socialLinksRepository
                .findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospital.getHospitalUUID())
                .stream()
                .map(SocialLinksMapper::toDto)
                .toList();

        response.setSocialLinks(socialLinks);

        // Address
        addressRepository
                .findByEntityTypeAndEntityId(
                        EntityType.HOSPITAL,
                        hospital.getHospitalUUID())
                .ifPresent(address ->
                        response.setAddress(
                                AddressMapper.toDto(address)));

        return response;
    }
}
