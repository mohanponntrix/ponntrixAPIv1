package com.ponntrix.hospital.dto.request;

import com.ponntrix.hospital.entity.EntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AddressRequestDto {

    private EntityType entityType;

    private UUID entityId;

    private Integer countryId;

    private Integer stateId;

    private Integer cityId;

    private Integer areaId;

    // PostGIS Coordinates
    private Double latitude;

    private Double longitude;

    private String landmark;

    private String addressLine1;

    private String addressLine2;

    private Integer pincode;

    private Boolean isPrimary;

    private Boolean isActive;

    private Integer createdBy;

    private Integer updatedBy;

}
