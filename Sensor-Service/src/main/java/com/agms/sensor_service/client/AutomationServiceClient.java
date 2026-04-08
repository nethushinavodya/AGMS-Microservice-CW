package com.agms.sensor_service.client;

import com.agms.sensor_service.dto.AutomationRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AutomationServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String AUTOMATION_URL = "http://localhost:8083/api/automation/process";

    public void sendToAutomation(AutomationRequestDTO requestDTO) {

        restTemplate.postForObject(
                AUTOMATION_URL,
                requestDTO,
                String.class
        );
    }
}