package com.agms.zone_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZoneResponseDTO {

    private Long id;
    private String zoneName;
    private double minTemp;
    private double maxTemp;
    private String deviceId;
}