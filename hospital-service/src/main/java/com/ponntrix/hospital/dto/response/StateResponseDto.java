package com.ponntrix.hospital.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StateResponseDto {

    private Integer stateId;

    private String stateName;

    private Integer countryId;

    private String countryName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
