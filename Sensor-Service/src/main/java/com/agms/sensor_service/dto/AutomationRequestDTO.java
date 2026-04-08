package com.agms.sensor_service.dto;

import lombok.Data;

@Data
public class AutomationRequestDTO {

    private String zoneId;
    private double temperature;
    private double humidity;
}