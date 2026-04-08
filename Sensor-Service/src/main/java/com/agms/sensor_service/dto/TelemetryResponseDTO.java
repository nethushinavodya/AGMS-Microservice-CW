package com.agms.sensor_service.dto;

import lombok.Data;

@Data
public class TelemetryResponseDTO {

    private String deviceId;
    private String zoneId;
    private Value value;

    @Data
    public static class Value {
        private double temperature;
        private double humidity;
    }
}