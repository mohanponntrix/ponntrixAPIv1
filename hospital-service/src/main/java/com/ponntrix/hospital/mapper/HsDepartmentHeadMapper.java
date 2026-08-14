package com.ponntrix.hospital.mapper;

import com.ponntrix.hospital.dto.request.HsDepartmentHeadRequestDTO;
import com.ponntrix.hospital.dto.response.HsDepartmentHeadResponseDTO;
import com.ponntrix.hospital.entity.HsDepartmentHead;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting Department Head DTOs
 * to entities and entities to DTOs.
 */
@Component
public class HsDepartmentHeadMapper {

    /**
     * Converts request DTO into entity.
     *
     * @param requestDTO request data
     * @return department head entity
     */
    public HsDepartmentHead toEntity(HsDepartmentHeadRequestDTO requestDTO) {

        HsDepartmentHead entity = new HsDepartmentHead();

        entity.setEffectiveFrom(requestDTO.getEffectiveFrom());

        entity.setEffectiveTo(requestDTO.getEffectiveTo());

        entity.setHospitalId(requestDTO.getHospitalId());

        entity.setDoctorId(requestDTO.getDoctorId());

        entity.setDepartmentsId(requestDTO.getDepartmentsId());

        return entity;
    }

    /**
     * Updates an existing entity using request DTO.
     *
     * @param entity     existing entity
     * @param requestDTO updated request data
     */
    public void updateEntity(HsDepartmentHead entity, HsDepartmentHeadRequestDTO requestDTO) {

        entity.setEffectiveFrom(requestDTO.getEffectiveFrom());

        entity.setEffectiveTo(requestDTO.getEffectiveTo());

        entity.setHospitalId(requestDTO.getHospitalId());

        entity.setDoctorId(requestDTO.getDoctorId());

        entity.setDepartmentsId(requestDTO.getDepartmentsId());
    }

    /**
     * Converts entity into response DTO.
     *
     * @param entity department head entity
     * @return response DTO
     */
    public HsDepartmentHeadResponseDTO toResponseDTO(HsDepartmentHead entity) {

        HsDepartmentHeadResponseDTO responseDTO = new HsDepartmentHeadResponseDTO();

//        responseDTO.setDepartmentHeadId(entity.getDepartmentHeadId());

        responseDTO.setDepartmentHeadUuid(entity.getDepartmentHeadUuid());

        responseDTO.setEffectiveFrom(entity.getEffectiveFrom());

        responseDTO.setEffectiveTo(entity.getEffectiveTo());

        responseDTO.setHospitalId(entity.getHospitalId());

        responseDTO.setDoctorId(entity.getDoctorId());

        responseDTO.setDepartmentsId(entity.getDepartmentsId());

        return responseDTO;
    }
}