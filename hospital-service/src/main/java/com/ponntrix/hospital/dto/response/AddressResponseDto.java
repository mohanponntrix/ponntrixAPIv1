package com.ponntrix.hospital.dto.response;

import com.ponntrix.hospital.entity.EntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponseDto {

    private UUID addressUUID;

    private UUID entityId;

    private EntityType entityType;

    // Country
    private Integer countryId;
    private String countryName;

    // State
    private Integer stateId;
    private String stateName;

    // City
    private Integer cityId;
    private String cityName;

    // Area
    private Integer areaId;
    private String areaName;

    // PostGIS Coordinates
    private Double latitude;

    private Double longitude;

    private String landmark;

    private String addressLine1;

    private String addressLine2;

    private Integer pincode;

    private Boolean isPrimary;

    private Boolean isActive;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
