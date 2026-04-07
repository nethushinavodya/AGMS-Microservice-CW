package com.agms.automation_service.dto;

import lombok.Data;

@Data
public class AutomationLogDTO {

    private Long id;
    private String zoneId;
    private double temperature;
    private String action;
    private String timestamp;
}