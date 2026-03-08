package com.agms.sensor_telemetry_service.controller;

import com.agms.sensor_telemetry_service.dto.SensorDataDTO;
import com.agms.sensor_telemetry_service.service.SensorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/latest")
    public SensorDataDTO getLatestReading() {
        return sensorService.getLastReading();
    }
}