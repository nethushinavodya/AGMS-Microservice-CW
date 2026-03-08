package com.agms.automation_service.controller;

import com.agms.automation_service.dto.SensorDataDTO;
import com.agms.automation_service.service.AutomationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {

    private final AutomationService automationService;

    public AutomationController(AutomationService automationService) {
        this.automationService = automationService;
    }

    @PostMapping("/process")
    public String processData(@RequestBody SensorDataDTO data) {

        automationService.processSensorData(data);

        return "Processed Successfully";
    }

    @GetMapping("/logs")
    public Object getLogs() {
        return automationService.getLogs();
    }
}