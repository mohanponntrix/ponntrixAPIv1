package com.ponntrix.hospital.serviceimpl;

import com.ponntrix.hospital.dto.request.HsDepartmentHeadRequestDTO;
import com.ponntrix.hospital.dto.response.HsDepartmentHeadResponseDTO;
import com.ponntrix.hospital.entity.HsDepartmentHead;
import com.ponntrix.hospital.exception.BadRequestException;
import com.ponntrix.hospital.exception.DuplicateResourceException;
import com.ponntrix.hospital.exception.ResourceNotFoundException;
import com.ponntrix.hospital.mapper.HsDepartmentHeadMapper;
import com.ponntrix.hospital.repository.HsDepartmentHeadRepository;
import com.ponntrix.hospital.service.HsDepartmentHeadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for Department Head APIs.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HsDepartmentHeadServiceImpl implements HsDepartmentHeadService {

    private final HsDepartmentHeadRepository departmentHeadRepository;

    private final HsDepartmentHeadMapper departmentHeadMapper;

    /**
     * Creates a new department head.
     */
    @Override
    @Transactional
    public HsDepartmentHeadResponseDTO createDepartmentHead(HsDepartmentHeadRequestDTO requestDTO) {

        log.info("Creating department head. hospitalId={}, doctorId={}, departmentsId={}", requestDTO.getHospitalId(), requestDTO.getDoctorId(), requestDTO.getDepartmentsId());

        validateEffectiveDates(requestDTO);

        /*
         * Check the unique combination before inserting.
         *
         * Database constraint:
         *
         * UNIQUE(departments_id, hospital_id, doctor_id)
         */
        boolean duplicateExists =
                departmentHeadRepository.existsByDepartmentsIdAndHospitalIdAndDoctorId
                        (requestDTO.getDepartmentsId(), requestDTO.getHospitalId(),
                                requestDTO.getDoctorId());

        if (duplicateExists) {

            log.warn("Duplicate department head assignment detected. hospitalId={}, doctorId={}, departmentsId={}", requestDTO.getHospitalId(), requestDTO.getDoctorId(), requestDTO.getDepartmentsId());

            throw new DuplicateResourceException("Department head assignment already exists for the given hospital, doctor and department");
        }

        /*
         * Convert DTO to entity.
         */
        HsDepartmentHead entity = departmentHeadMapper.toEntity(requestDTO);

        /*
         * UUID is generated using @PrePersist
         * inside the entity.
         */
        HsDepartmentHead savedEntity = departmentHeadRepository.save(entity);

        log.info("Department head created successfully. departmentHeadId={}", savedEntity.getDepartmentHeadId());

        return departmentHeadMapper.toResponseDTO(savedEntity);
    }

//    ==================================================================================
    /**
     * Retrieves all department heads.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HsDepartmentHeadResponseDTO> getAllDepartmentHeads() {

        log.info("Fetching all department heads");

        List<HsDepartmentHead> entityList = departmentHeadRepository.findAll();

        List<HsDepartmentHeadResponseDTO> responseList = new ArrayList<>();

        /*
         * Intentionally using a normal for loop instead
         * of streams as per project requirement.
         */
        for (HsDepartmentHead entity : entityList) {

            HsDepartmentHeadResponseDTO responseDTO = departmentHeadMapper.toResponseDTO(entity);

            responseList.add(responseDTO);
        }

        log.info("Department heads fetched successfully. count={}", responseList.size());

        return responseList;
    }
//====================================================================================
    /**
     * Retrieves one department head by UUID.
     *
     * UUID is used as the external API identifier.
     */
    @Override
    @Transactional(readOnly = true)
    public HsDepartmentHeadResponseDTO getDepartmentHeadByUuid(
            UUID departmentHeadUuid
    ) {

        log.info(
                "Fetching department head. departmentHeadUuid={}",
                departmentHeadUuid
        );

        HsDepartmentHead entity =
                departmentHeadRepository
                        .findByDepartmentHeadUuid(departmentHeadUuid)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Department head not found with uuid: "
                                                + departmentHeadUuid
                                )
                        );

        return departmentHeadMapper.toResponseDTO(entity);
    }
//    =================================================================================
    /**
     * Updates an existing department head.
     *
     * UUID is used by the external API.
     * Internal department_head_id is used for database logic.
     */
    @Override
    @Transactional
    public HsDepartmentHeadResponseDTO updateDepartmentHeadDetailsByUuid(
            UUID departmentHeadUuid,
            HsDepartmentHeadRequestDTO requestDTO
    ) {

        log.info(
                "Updating department head. departmentHeadUuid={}",
                departmentHeadUuid
        );

        validateEffectiveDates(requestDTO);

        /*
         * Find the record using the public UUID.
         *
         * UUID is used externally by the API.
         */
        HsDepartmentHead entity =
                departmentHeadRepository
                        .findByDepartmentHeadUuid(departmentHeadUuid)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Department head not found with uuid: "
                                                + departmentHeadUuid
                                )
                        );

        /*
         * Check whether another record already contains
         * the requested hospital + doctor + department
         * combination.
         *
         * The internal primary key is used here to exclude
         * the current record from the duplicate check.
         */
        boolean duplicateExists =
                departmentHeadRepository
                        .existsByDepartmentsIdAndHospitalIdAndDoctorIdAndDepartmentHeadIdNot(
                                requestDTO.getDepartmentsId(),
                                requestDTO.getHospitalId(),
                                requestDTO.getDoctorId(),
                                entity.getDepartmentHeadId()
                        );

        if (duplicateExists) {

            log.warn(
                    "Duplicate department head assignment detected during update. departmentHeadUuid={}",
                    departmentHeadUuid
            );

            throw new DuplicateResourceException(
                    "Another department head assignment already exists for the given hospital, doctor and department"
            );
        }

        /*
         * Update entity fields.
         */
        departmentHeadMapper.updateEntity(entity, requestDTO);

        /*
         * Save updated entity.
         */
        HsDepartmentHead updatedEntity = departmentHeadRepository.save(entity);

        log.info(
                "Department head updated successfully. departmentHeadUuid={}",
                departmentHeadUuid
        );

        return departmentHeadMapper.toResponseDTO(updatedEntity);
    }
//=====================================================================================
    /**
     * Validates the effective date range.
     * <p>
     * effectiveTo cannot be before effectiveFrom.
     */
    private void validateEffectiveDates(HsDepartmentHeadRequestDTO requestDTO) {

        if (requestDTO.getEffectiveFrom() != null && requestDTO.getEffectiveTo()
                != null && requestDTO.getEffectiveTo().isBefore(requestDTO.getEffectiveFrom())) {

            log.warn("Invalid effective date range");

            throw new BadRequestException("Effective to date cannot be before effective from date");
        }
    }
}