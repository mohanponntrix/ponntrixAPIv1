package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.dto.SocialLinksDto;
import com.ponntrix.hospital.entity.Documents;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.Doctor;
import com.ponntrix.hospital.entity.DoctorSpecialization;
import com.ponntrix.hospital.entity.Qualification;
import com.ponntrix.hospital.entity.Specialization;
import com.ponntrix.hospital.exception.ResourceNotFoundException;
import com.ponntrix.hospital.mapper.*;
import com.ponntrix.hospital.repository.*;
import com.ponntrix.hospital.dto.request.DoctorRequestDto;
import com.ponntrix.hospital.dto.DoctorSpecializationDto;
import com.ponntrix.hospital.dto.response.DoctorResponseDto;
import com.ponntrix.hospital.dto.DocumentsDto;
import com.ponntrix.hospital.service.DoctorService;
import com.ponntrix.hospital.service.S3Service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final QualificationRepository qualificationRepository;
    private final DoctorSpecializationRepository doctorSpecializationRepository;
    private final SpecializationRepository specializationRepository;
    private final DocumentsRepository documentsRepository;
    private final SocialLinksRepository socialLinksRepository;
    private final AddressRepository addressRepository;
    private final S3Service s3Service;

    @Override
    public DoctorResponseDto createDoctor(
            DoctorRequestDto dto,
            MultipartFile profilePic,
            MultipartFile digitalSignature) throws IOException {

        if(doctorRepository.existsByDoctorEmail(dto.getDoctorEmail())){
            throw new RuntimeException("Email already exists");
        }

        if(doctorRepository.existsByDoctorPhone(dto.getDoctorPhone())){
            throw new RuntimeException("Phone already exists");
        }

        if(dto.getRegistrationNumber()!=null &&
                doctorRepository.existsByRegistrationNumber(dto.getRegistrationNumber())){
            throw new RuntimeException("Registration Number already exists");
        }

        Doctor doctor = DoctorMapper.toEntity(dto);

        if(profilePic!=null && !profilePic.isEmpty()){

            String folder = "doctor-profile-pics";

            String imageUrl = s3Service.uploadFile(profilePic,folder);

            doctor.setProfilePicUrl(imageUrl);
        }
        if(digitalSignature!=null && !digitalSignature.isEmpty()){

            String folder = "doctor-digital-signature";

            String imageUrl = s3Service.uploadFile(digitalSignature,folder);

            doctor.setDigitalSignatureUrl(imageUrl);
        }

        Qualification qualification = qualificationRepository
                .findById(dto.getQualificationId())
                .orElseThrow(() -> new RuntimeException("Qualification Not Found"));
        doctor.setQualification(qualification);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return buildDoctorResponse(savedDoctor);
    }



    @Override
    public List<DoctorResponseDto> getAllDoctors() {

        return doctorRepository.findByIsActiveTrue()
                .stream()
                .map(this::buildDoctorResponse)
                .toList();
    }


    @Override
    public DoctorResponseDto getDoctorById(UUID doctorId) {

        Doctor doctor = doctorRepository.findByDoctorUUID(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found."));

        if (!Boolean.TRUE.equals(doctor.getIsActive())) {
            throw new ResourceNotFoundException("Doctor is inactive.");
        }

        return buildDoctorResponse(doctor);
    }


    @Override
    public DoctorResponseDto updateDoctor(
            UUID doctorId,
            DoctorRequestDto dto,
            MultipartFile profilePic,
            MultipartFile digitalSignature) throws IOException {

        Doctor doctor = doctorRepository.findByDoctorUUID(doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor Not Found"));

        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setGender(dto.getGender());
        doctor.setDoctorPhone(dto.getDoctorPhone());
        doctor.setDoctorEmail(dto.getDoctorEmail());
        doctor.setRegistrationNumber(dto.getRegistrationNumber());
        doctor.setExperienceInYears(dto.getExperienceInYears());
        doctor.setUpdatedBy(dto.getUpdatedBy());

        Qualification qualification =
                qualificationRepository.findById(dto.getQualificationId())
                        .orElseThrow(() ->
                                new RuntimeException("Qualification Not Found"));
        doctor.setQualification(qualification);


        if(profilePic!=null && !profilePic.isEmpty()){

            String folder = "doctor-profile-pics";
            String imageUrl = s3Service.uploadFile(profilePic,folder);

            doctor.setProfilePicUrl(imageUrl);
        }

        if(digitalSignature!=null && !digitalSignature.isEmpty()){

            String folder = "doctor-digital-signature";
            String imageUrl = s3Service.uploadFile(profilePic,folder);

            doctor.setDigitalSignatureUrl(imageUrl);
        }
        Doctor updatedDoctor = doctorRepository.save(doctor);

        return buildDoctorResponse(updatedDoctor);
    }


    @Transactional
    @Override
    public void deleteDoctor(UUID doctorId) {
        Doctor doctor = doctorRepository.findByDoctorUUID(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));

        doctorSpecializationRepository.deleteByDoctorDoctorUUID(doctor.getDoctorUUID());

        addressRepository.deleteByEntityTypeAndEntityId(EntityType.DOCTOR, doctor.getDoctorUUID());

        List<Documents> documents =
                documentsRepository.findByEntityTypeAndEntityId(EntityType.DOCTOR, doctor.getDoctorUUID());

        for (Documents document : documents) {
            s3Service.deleteFile(document.getDocumentUrlLink());
        }
        documentsRepository.deleteAll(documents);
        doctorRepository.delete(doctor);
    }


    @Override
    @Transactional
    public List<DoctorSpecializationDto>
    updateDoctorSpecializations(UUID doctorId, List<DoctorSpecializationDto> specializations) {

        Doctor doctor = doctorRepository.findByDoctorUUID(doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found."));

        if (specializations == null || specializations.isEmpty()) {
            throw new RuntimeException("At least one specialization is required.");
        }

        long primaryCount =
                specializations.stream()
                        .filter(dto ->
                                Boolean.TRUE.equals(
                                        dto.getIsPrimary()))
                        .count();

        if (primaryCount != 1) {
            throw new RuntimeException("Exactly one primary specialization is required.");
        }

        // Check duplicate specialization IDs
        long uniqueCount =
                specializations.stream()
                        .map(
                                DoctorSpecializationDto
                                        ::getSpecializationId
                        )
                        .distinct()
                        .count();

        if (uniqueCount != specializations.size()) {

            throw new RuntimeException("Duplicate specialization selected.");
        }

        /*
         * Remove existing mappings.
         *
         * This makes the request behave as a complete
         * replacement of the doctor's specializations.
         */
        doctorSpecializationRepository
                .deleteByDoctorDoctorUUID(doctorId);

        /*
         * Create new mappings
         */
        List<DoctorSpecialization> mappings =
                specializations.stream()
                        .map(dto -> {

                            Specialization specialization =
                                    specializationRepository
                                            .findById(
                                                    dto.getSpecializationId()
                                            )
                                            .orElseThrow(() ->
                                                    new RuntimeException(
                                                            "Specialization not found with id: "
                                                                    + dto.getSpecializationId()
                                                    ));

                            DoctorSpecialization mapping =
                                    DoctorSpecializationMapper
                                            .toEntity(dto);

                            mapping.setDoctor(doctor);

                            mapping.setSpecialization(
                                    specialization
                            );

                            return mapping;
                        })
                        .toList();

        List<DoctorSpecialization> savedMappings =
                doctorSpecializationRepository
                        .saveAll(mappings);

        return savedMappings.stream()
                .map(
                        DoctorSpecializationMapper::toDto
                )
                .toList();
    }


    private DoctorResponseDto buildDoctorResponse(Doctor doctor) {

        DoctorResponseDto response = DoctorMapper.toDto(doctor);

        // Specializations
        List<DoctorSpecializationDto> specializations =
                doctorSpecializationRepository
                        .findByDoctorDoctorUUID(
                                doctor.getDoctorUUID())
                        .stream()
                        .map(DoctorSpecializationMapper::toDto)
                        .toList();

        response.setSpecializations(specializations);


        // Documents
        List<DocumentsDto> documents = documentsRepository
                .findByEntityTypeAndEntityId(
                        EntityType.DOCTOR,
                        doctor.getDoctorUUID())
                .stream()
                .map(DocumentsMapper::toDto)
                .toList();

        response.setDocuments(documents);

        // Address
        addressRepository
                .findByEntityTypeAndEntityId(
                        EntityType.DOCTOR,
                        doctor.getDoctorUUID())
                .ifPresent(address ->
                        response.setAddress(
                                AddressMapper.toDto(address)));

        // Social Links
        List<SocialLinksDto> socialLinks = socialLinksRepository
                .findByEntityTypeAndEntityId(
                        EntityType.DOCTOR,
                        doctor.getDoctorUUID())
                .stream()
                .map(SocialLinksMapper::toDto)
                .toList();

        response.setSocialLinks(socialLinks);

        return response;
    }
}
