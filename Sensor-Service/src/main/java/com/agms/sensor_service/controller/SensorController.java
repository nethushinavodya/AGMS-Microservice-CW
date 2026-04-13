package com.agms.sensor_service.controller;

import com.agms.sensor_service.dto.TelemetryResponseDTO;
import com.agms.sensor_service.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping("/latest")
    public TelemetryResponseDTO getLatestData() {
        return sensorService.getLatestData();
    }

    @PostMapping("/register")
    public String registerDevice() {
        // Minimal local implementation: generate an ID and let Zone-Service store it.
        // This can be replaced later with real device provisioning logic.
        return UUID.randomUUID().toString();
    }
}