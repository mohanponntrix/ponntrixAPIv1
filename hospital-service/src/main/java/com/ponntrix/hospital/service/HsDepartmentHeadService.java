package com.ponntrix.hospital.service;

import com.ponntrix.hospital.dto.request.HsDepartmentHeadRequestDTO;
import com.ponntrix.hospital.dto.response.HsDepartmentHeadResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Department Head operations.
 */
public interface HsDepartmentHeadService {

    /**
     * Creates a new department head.
     *
     * @param requestDTO request data
     * @return created department head
     */
    HsDepartmentHeadResponseDTO createDepartmentHead(
            HsDepartmentHeadRequestDTO requestDTO
    );

    /**
     * Updates an existing department head.
     *
     * @param departmentHeadUuid department head UUID
     * @param requestDTO updated data
     * @return updated department head
     */
    HsDepartmentHeadResponseDTO updateDepartmentHeadDetailsByUuid(
            UUID departmentHeadUuid,
            HsDepartmentHeadRequestDTO requestDTO
    );

    /**
     * Retrieves all department heads.
     *
     * @return list of department heads
     */
    List<HsDepartmentHeadResponseDTO> getAllDepartmentHeads();

    /**
     * Retrieves department head by ID.
     *
     * @param departmentHeadUuid department head UUID
     * @return department head
     */
    HsDepartmentHeadResponseDTO getDepartmentHeadByUuid(
            UUID departmentHeadUuid
    );
}