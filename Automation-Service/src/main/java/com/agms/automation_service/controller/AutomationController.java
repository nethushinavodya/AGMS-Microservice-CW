package com.agms.automation_service.controller;

import com.agms.automation_service.dto.AutomationLogDTO;
import com.agms.automation_service.dto.SensorDataDTO;
import com.agms.automation_service.entity.AutomationLog;
import com.agms.automation_service.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;

    @PostMapping("/process")
    public String processSensorData(@RequestBody SensorDataDTO sensorDataDTO) {
        return automationService.processSensorData(sensorDataDTO);
    }

    @GetMapping("/logs")
    public List<AutomationLogDTO> getLogs() {
        return automationService.getAllLogs();
    }
}