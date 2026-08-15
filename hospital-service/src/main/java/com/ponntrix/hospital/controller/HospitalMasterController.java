package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.*;
import com.ponntrix.hospital.dto.request.*;
import com.ponntrix.hospital.dto.response.*;
import com.ponntrix.hospital.entity.DayOfWeek;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.repository.DayOfWeekRepository;
import com.ponntrix.hospital.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class HospitalMasterController {

    private final HospitalService hospitalService;
    private final DepartmentsService departmentsService;
    private final DoctorService doctorService;
    private final QualificationService qualificationService;
    private final SpecializationService specializationService;
    private final OperatingHoursService operatingHoursService;
    private final HospitalFacilitiesService hospitalFacilitiesService;
    private final SocialLinksService socialLinksService;
    private final DayOfWeekRepository dayOfWeekRepository;
    private final ObjectMapper objectMapper;


//=============================
//Hospitals Controller
//=============================


    @PostMapping(value = "/hospitals/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HospitalResponseDto> createHospital(
            @RequestPart("hospital") String hospitalJson,
            @RequestPart("logo") MultipartFile logo,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "documentFiles", required = false)
            List<MultipartFile> documentFiles) throws IOException {

        HospitalRequestDto dto =
                objectMapper.readValue(
                        hospitalJson,
                        HospitalRequestDto.class
                );

        return ResponseEntity.ok(hospitalService.createHospital(dto, logo, coverImage,documentFiles));
    }


    @GetMapping("/hospitals")
    public ResponseEntity<List<HospitalResponseDto>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }


    @GetMapping("/hospitals/{hospitalId}")
    public ResponseEntity<HospitalResponseDto> getHospitalById(@PathVariable UUID hospitalId) {
        return ResponseEntity.ok(hospitalService.getHospitalById(hospitalId));
    }


    @PutMapping(value = "/hospitals/{hospitalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HospitalResponseDto> updateHospital(
            @PathVariable UUID hospitalId,
            @RequestPart("hospital") String hospitalJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "documentFiles", required = false)
            List<MultipartFile> documentFiles)throws IOException {

        HospitalRequestDto dto =
                objectMapper.readValue(
                        hospitalJson,
                        HospitalRequestDto.class
                );

        return ResponseEntity.ok(hospitalService.updateHospital(
                hospitalId, dto, logo, coverImage,documentFiles));
    }

    @PostMapping(value = "/hospitals/{hospitalId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentsDto> uploadHospitalDocument(
            @PathVariable UUID hospitalId,
            @RequestPart("document") String documentJson,
            @RequestPart("file") MultipartFile file) throws IOException {

        DocumentsDto dto = objectMapper.readValue(documentJson, DocumentsDto.class);

        return ResponseEntity.ok(hospitalService.uploadHospitalDocument(hospitalId, dto, file));
    }

    @DeleteMapping("/hospitals/{hospitalId}")
    public ResponseEntity<String> deleteHospital(@PathVariable UUID hospitalId) {
        hospitalService.deleteHospital(hospitalId);
        return ResponseEntity.noContent().build();
    }


//=============================
//Departments Controllers
//=============================


    @GetMapping("/departments")
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentsService.getAllDepartments();
    }

    @PostMapping(value = "/departments/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DepartmentResponseDto> createDepartment(
            @ModelAttribute DepartmentRequestDto requestDto,
            @RequestParam MultipartFile icon) throws IOException {
        return ResponseEntity.ok(departmentsService.createDepartment(requestDto,icon)
        );
    }

    @GetMapping("/departments/{id}")
    public DepartmentResponseDto getDepartmentById(@PathVariable Integer id) {
        return departmentsService.getDepartmentById(id);
    }

    @PutMapping(value = "/departments/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DepartmentResponseDto> updateDepartment(
            @PathVariable Integer id,
            @ModelAttribute DepartmentRequestDto dto,
            @RequestParam(required = false) MultipartFile icon)throws IOException {
        return ResponseEntity.ok(departmentsService.updateDepartment(id,dto,icon)
        );
    }

    @DeleteMapping("/departments/delete/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Integer id) {
        departmentsService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully.");
    }


//=============================
//Doctor Controllers
//=============================


    @PostMapping(value="/doctors/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DoctorResponseDto createDoctor(
            @RequestPart(value = "doctor") String doctorJson,
            @RequestPart(value = "profilePic",required = false)
            MultipartFile profilePic,
            @RequestPart(value = "digitalSignature",required = false)
            MultipartFile digitalSignature) throws IOException {

        DoctorRequestDto dto =
                objectMapper.readValue(
                        doctorJson,
                        DoctorRequestDto.class
                );
        return doctorService.createDoctor(dto,profilePic,digitalSignature);
    }


    @GetMapping("/doctors")
    public List<DoctorResponseDto> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/doctors/{doctorId}")
    public DoctorResponseDto getDoctorById(@PathVariable UUID doctorId){
        return doctorService.getDoctorById(doctorId);
    }

    @PutMapping(value="/doctors/update/{doctorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DoctorResponseDto updateDoctor(
            @PathVariable UUID doctorId,
            @RequestPart(value = "doctor") String doctorJson,
            @RequestPart(value = "profilePic",required = false)
            MultipartFile profilePic,
            @RequestPart(value = "digitalSignature",required = false)
            MultipartFile digitalSignature) throws IOException {
        DoctorRequestDto dto =
                objectMapper.readValue(
                        doctorJson,
                        DoctorRequestDto.class
                );
        return doctorService.updateDoctor(doctorId,dto,profilePic,digitalSignature);
    }

    @DeleteMapping("/doctors/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable UUID doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @PostMapping("/{doctorId}/specializations")
    public ResponseEntity<List<DoctorSpecializationDto>>
    updateDoctorSpecializations(@PathVariable UUID doctorId, @RequestBody
            List<DoctorSpecializationDto> specializations) {

        return ResponseEntity.ok(doctorService.updateDoctorSpecializations(doctorId, specializations));
    }


//=============================
//Qualifications Controller
//=============================


    @PostMapping("/qualifications/create")
    public ResponseEntity<QualificationResponseDto> createQualification(
            @RequestBody QualificationRequestDto dto) {
        QualificationResponseDto response = qualificationService.createQualification(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/qualifications")
    public ResponseEntity<List<QualificationResponseDto>> getAllQualifications() {
        return ResponseEntity.ok(qualificationService.getAllQualifications());
    }

    @GetMapping("/qualifications/{id}")
    public ResponseEntity<QualificationResponseDto> getQualificationById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(qualificationService.getQualificationById(id));
    }

    @PutMapping("/qualifications/update/{id}")
    public ResponseEntity<QualificationResponseDto> updateQualification(
            @PathVariable Integer id,
            @RequestBody QualificationRequestDto dto) {
        return ResponseEntity.ok(qualificationService.updateQualification(id, dto));
    }

    @DeleteMapping("/qualifications/delete/{id}")
    public ResponseEntity<String> deleteQualification(@PathVariable Integer id) {
        qualificationService.deleteQualification(id);
        return ResponseEntity.ok("Qualification deleted successfully.");
    }


//=============================
//Specializations Controller
//=============================


    @PostMapping("/specializations/create")
    public ResponseEntity<SpecializationResponseDto> createSpecialization(
            @RequestBody SpecializationRequestDto dto) {
        return ResponseEntity.ok(specializationService.createSpecialization(dto));
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<SpecializationResponseDto>> getAllSpecializations() {
        return ResponseEntity.ok(specializationService.getAllSpecializations());
    }

    @GetMapping("/specializations/{specializationId}")
    public ResponseEntity<SpecializationResponseDto> getSpecializationById(
            @PathVariable Integer specializationId) {
        return ResponseEntity.ok(specializationService.getSpecializationById(specializationId));
    }

    @PutMapping("/specializations/{specializationId}")
    public ResponseEntity<SpecializationResponseDto> updateSpecialization(
            @PathVariable Integer specializationId,
            @RequestBody SpecializationRequestDto dto) {
        return ResponseEntity.ok(specializationService.updateSpecialization(specializationId, dto));
    }

    @DeleteMapping("/specializations/{specializationId}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable Integer specializationId) {
        specializationService.deleteSpecialization(specializationId);
        return ResponseEntity.ok("Specialization deleted successfully.");
    }


//=============================
//Operating_Hours Controller
//=============================


        @PostMapping("/operating-hours/create")
        public ResponseEntity<OperatingHoursDto> createOperatingHours(
                @RequestBody OperatingHoursDto dto) {

            return ResponseEntity.status(HttpStatus.CREATED).body(
                            operatingHoursService.createOperatingHours(dto));
        }


        @GetMapping("/operating-hours")
        public ResponseEntity<List<OperatingHoursDto>>
        getOperatingHours(
                @RequestParam EntityType entityType,
                @RequestParam UUID entityId) {
            return ResponseEntity.ok(operatingHoursService.getOperatingHours(entityType, entityId));
        }


        @PutMapping("/operating-hours/{operatingHoursId}")
        public ResponseEntity<OperatingHoursDto>
        updateOperatingHours(
                @PathVariable UUID operatingHoursId,
                @RequestBody OperatingHoursDto dto) {

            return ResponseEntity.ok(operatingHoursService.updateOperatingHours(
                    operatingHoursId, dto));
        }


        @DeleteMapping("/operating-hours/{operatingHoursId}")
        public ResponseEntity<String>
        deleteOperatingHours(@PathVariable UUID operatingHoursId) {
            operatingHoursService.deleteOperatingHours(operatingHoursId);
            return ResponseEntity.ok("Operating hours deleted successfully.");
        }

    @GetMapping("/day-of-week")
    public ResponseEntity<List<DayOfWeek>> getAllDays() {
        return ResponseEntity.ok(dayOfWeekRepository.findAllByOrderByDayOfWeekIdAsc());
    }


//=============================
//Hospital_Facilities Controller
//=============================


        @PostMapping(value = "/hospitals/{hospitalId}/facilities",
                     consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<HospitalFacilitiesDto>
        createFacility(@PathVariable UUID hospitalId,
                       @ModelAttribute HospitalFacilitiesDto dto,
                       @RequestPart(value = "icon", required = false) MultipartFile icon) throws IOException {
        return ResponseEntity.ok(hospitalFacilitiesService.createFacility(hospitalId, dto, icon));}


        @GetMapping("/hospitals/{hospitalId}/facilities")
        public ResponseEntity<List<HospitalFacilitiesDto>>
        getFacilities(@PathVariable UUID hospitalId) {
            return ResponseEntity.ok(hospitalFacilitiesService.getFacilities(hospitalId));}


        @GetMapping("/hospitals/{hospitalId}/facilities/active")
        public ResponseEntity<List<HospitalFacilitiesDto>>
        getActiveFacilities(@PathVariable UUID hospitalId) {
            return ResponseEntity.ok(hospitalFacilitiesService.getActiveFacilities(hospitalId));}


        @PutMapping(value = "/hospitals/facilities/{facilityId}",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<HospitalFacilitiesDto>
        updateFacility(
                @PathVariable UUID facilityId,
                @ModelAttribute HospitalFacilitiesDto dto,
                @RequestPart(value = "icon", required = false) MultipartFile icon) throws IOException {
            return ResponseEntity.ok(hospitalFacilitiesService.updateFacility(facilityId, dto, icon));}


        @DeleteMapping("/hospitals/facilities/{facilityId}")
        public ResponseEntity<String> deleteFacility(
                @PathVariable UUID facilityId) {
            hospitalFacilitiesService.deleteFacility(facilityId);
            return ResponseEntity.ok("Hospital facility deleted successfully.");}


    //=============================
    //Social Links Controller
    //=============================


        @PostMapping("/social-links")
        public ResponseEntity<SocialLinksDto>
        createSocialLink(@RequestBody SocialLinksDto dto) {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(socialLinksService.createSocialLink(dto));
        }

        @GetMapping("/social-links")
        public ResponseEntity<List<SocialLinksDto>>
        getSocialLinks(@RequestParam EntityType entityType, @RequestParam UUID entityId) {

            return ResponseEntity.ok(socialLinksService.getSocialLinks(entityType, entityId));
        }

        @PutMapping("/social-links/{socialLinksId}")
        public ResponseEntity<SocialLinksDto>
        updateSocialLink(@PathVariable UUID socialLinksId,
                         @RequestBody SocialLinksDto dto) {

            return ResponseEntity.ok(socialLinksService.updateSocialLink(socialLinksId, dto));
        }

        @DeleteMapping("/social-links/{socialLinksId}")
        public ResponseEntity<String>
        deleteSocialLink(@PathVariable UUID socialLinksId) {

            socialLinksService.deleteSocialLink(socialLinksId);
            return ResponseEntity.ok("Social link deleted successfully.");
        }
}
