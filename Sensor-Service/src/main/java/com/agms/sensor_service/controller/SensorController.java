package com.agms.sensor_service.controller;

import com.agms.sensor_service.dto.TelemetryResponseDTO;
import com.agms.sensor_service.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping("/latest")
    public TelemetryResponseDTO getLatestData() {
        return sensorService.getLatestData();
    }
}