package com.ponntrix.hospital.controller;

import com.ponntrix.hospital.dto.request.HsDepartmentHeadRequestDTO;
import com.ponntrix.hospital.dto.response.HsDepartmentHeadResponseDTO;
import com.ponntrix.hospital.service.HsDepartmentHeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for Department Head APIs.
 */
@RestController
@RequestMapping("/api/hs/department-heads")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Hs Department Head", description = "APIs for managing hospital department heads")
public class HsDepartmentHeadController {

    private final HsDepartmentHeadService departmentHeadService;

    /**
     * Creates a new department head assignment.
     */
    @PostMapping("/createDepartmentHead")
    @Operation(summary = "Create Department Head", description = "Creates a new department head assignment")
    @ApiResponses(
            {@ApiResponse(responseCode = "201", description = "Department head created successfully",
            content =
            @Content(schema = @Schema(implementation = HsDepartmentHeadResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or database constraint violation"),
            @ApiResponse(responseCode = "409", description = "Department head assignment already exists")
            })
    
    public ResponseEntity<HsDepartmentHeadResponseDTO> createDepartmentHead
    (@Valid @RequestBody HsDepartmentHeadRequestDTO requestDTO) {

        log.info("POST /api/hs/department-heads request received");

        HsDepartmentHeadResponseDTO response = departmentHeadService.createDepartmentHead(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    ==================================================================================

    /**
     * Retrieves all department heads.
     */
    @GetMapping("/getAllDepartmentHeads")
    @Operation(summary = "Get All Department Heads", description = "Retrieves all department head assignments")
    @ApiResponse(responseCode = "200", description = "Department heads retrieved successfully")
    public ResponseEntity<List<HsDepartmentHeadResponseDTO>> getAllDepartmentHeads() {

        log.info("GET /api/hs/department-heads request received");

        List<HsDepartmentHeadResponseDTO> response = departmentHeadService.getAllDepartmentHeads();

        return ResponseEntity.ok(response);
    }

//    ==============================================================================

    /**
     * Retrieves one department head by UUID.
     * <p>
     * UUID is used as the external API identifier.
     */
    @GetMapping("getDepartmentHeadByUuid/{departmentHeadUuid}")
    @Operation(summary = "Get Department Head By UUID", description = "Retrieves a department head using its UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department head retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Department head not found")
    })
    public ResponseEntity<HsDepartmentHeadResponseDTO> getDepartmentHeadByUuid(

            @Parameter(description = "Department head UUID", required = true, example = "550e8400-e29b-41d4-a716-446655440000") @PathVariable UUID departmentHeadUuid) {

        log.info("GET /api/hs/department-heads/{} request received", departmentHeadUuid);

        HsDepartmentHeadResponseDTO response = departmentHeadService.getDepartmentHeadByUuid(departmentHeadUuid);

        return ResponseEntity.ok(response);
    }

//    ================================================================================

    /**
     * Updates an existing department head assignment.
     * <p>
     * UUID is used as the external API identifier.
     */
    @PutMapping("updateDepartmentHeadDetailsByUuid/{departmentHeadUuid}")
    @Operation(summary = "Update Department Head By UUID", description = "Updates an existing department head assignment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department head updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Department head not found"),
            @ApiResponse(responseCode = "409", description = "Duplicate department head assignment")})
    public ResponseEntity<HsDepartmentHeadResponseDTO> updateDepartmentHeadDetailsByUuid(@Parameter(description = "Department head UUID", required = true, example = "550e8400-e29b-41d4-a716-446655440000") @PathVariable UUID departmentHeadUuid, @Valid @RequestBody HsDepartmentHeadRequestDTO requestDTO) {

        log.info("PUT /api/hs/department-heads/{} request received", departmentHeadUuid);

        HsDepartmentHeadResponseDTO response = departmentHeadService.updateDepartmentHeadDetailsByUuid(departmentHeadUuid, requestDTO);

        return ResponseEntity.ok(response);
    }
}