package com.agms.sensor_telemetry_service.feign;

import com.agms.sensor_telemetry_service.dto.SensorDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

// Use configurable automation service URL; default to http://localhost:8099/api/automation
@FeignClient(name = "automation-service", url = "${automation.service.url:http://localhost:8099/api/automation}")
public interface AutomationClient {

    @PostMapping("/process")
    String sendSensorData(SensorDataDTO data);
}