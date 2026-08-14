package com.ponntrix.hospital.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Request DTO used for creating and updating
 * department head records.
 */
@Getter
@Setter
@Schema(
        name = "HsDepartmentHeadRequestDTO",
        description = "Request payload for department head"
)
public class HsDepartmentHeadRequestDTO {

    /**
     * Date from which the doctor becomes department head.
     */
    @Schema(
            description = "Date and time from which the assignment is effective",
            example = "2026-08-01T09:00:00+05:30"
    )
    private OffsetDateTime effectiveFrom;

    /**
     * Date until which the doctor remains department head.
     */
    @Schema(
            description = "Date and time until which the assignment is effective",
            example = "2027-07-31T18:00:00+05:30"
    )
    private OffsetDateTime effectiveTo;

    /**
     * Hospital ID.
     */
    @NotNull(message = "Hospital ID is required")
    @Positive(message = "Hospital ID must be greater than zero")
    @Schema(
            description = "ID of the hospital",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer hospitalId;

    /**
     * Doctor ID.
     */
    @NotNull(message = "Doctor ID is required")
    @Positive(message = "Doctor ID must be greater than zero")
    @Schema(
            description = "ID of the doctor",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer doctorId;

    /**
     * Department ID.
     */
    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be greater than zero")
    @Schema(
            description = "ID of the department",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer departmentsId;
}