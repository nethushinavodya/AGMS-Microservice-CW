package com.agms.zone_service.dto;

import lombok.Data;

@Data
public class ZoneRequestDTO {

    private String zoneName;
    private double minTemp;
    private double maxTemp;
}