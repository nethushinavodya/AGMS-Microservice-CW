package com.agms.automation_service.dto;

import lombok.Data;

@Data
public class ZoneDTO {

    private String id;
    private String zoneName;
    private double minTemp;
    private double maxTemp;
}