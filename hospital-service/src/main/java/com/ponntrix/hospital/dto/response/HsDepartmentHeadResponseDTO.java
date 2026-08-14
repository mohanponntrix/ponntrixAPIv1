package com.ponntrix.hospital.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response DTO for department head APIs.
 */
@Getter
@Setter
@Schema(
        name = "HsDepartmentHeadResponseDTO",
        description = "Response payload containing department head details"
)
public class HsDepartmentHeadResponseDTO {

    /**
     * Primary key.
     */
//    @Schema(
//            description = "Department head ID",
//            example = "1"
//    )
//    private Integer departmentHeadId;

    /**
     * Unique UUID.
     */
    @Schema(
            description = "Unique UUID of department head",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private UUID departmentHeadUuid;

    /**
     * Effective start date.
     */
    @Schema(
            description = "Assignment effective start date",
            example = "2027-07-31T18:00:00+05:30(UTC) or 2026-08-01T09:00:00Z(ITC) "
    )
    private OffsetDateTime effectiveFrom;

    /**
     * Effective end date.
     */
    @Schema(
            description = "Assignment effective end date",
            example = "2027-07-31T18:00:00+05:30(UTC) or 2026-08-01T09:00:00Z(ITC) "
    )
    private OffsetDateTime effectiveTo;

    /**
     * Hospital ID.
     */
    @Schema(
            description = "Hospital ID",
            example = "1"
    )
    private Integer hospitalId;

    /**
     * Doctor ID.
     */
    @Schema(
            description = "Doctor ID",
            example = "1"
    )
    private Integer doctorId;

    /**
     * Department ID.
     */
    @Schema(
            description = "Department ID",
            example = "1"
    )
    private Integer departmentsId;
}