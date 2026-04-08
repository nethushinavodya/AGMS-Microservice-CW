package com.agms.sensor_service.client;

import com.agms.sensor_service.dto.AuthRequestDTO;
import com.agms.sensor_service.dto.AuthResponseDTO;
import com.agms.sensor_service.dto.TelemetryResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Component
public class ExternalIoTClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String BASE_URL = "http://104.211.95.241:8080/api";

    public AuthResponseDTO login(AuthRequestDTO request) {

        HttpEntity<AuthRequestDTO> entity = new HttpEntity<>(request);

        ResponseEntity<AuthResponseDTO> response = restTemplate.exchange(
                BASE_URL + "/auth/login",
                HttpMethod.POST,
                entity,
                AuthResponseDTO.class
        );

        return response.getBody();
    }

    public TelemetryResponseDTO getTelemetry(String token, String deviceId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TelemetryResponseDTO> response = restTemplate.exchange(
                BASE_URL + "/devices/telemetry/" + deviceId,
                HttpMethod.GET,
                entity,
                TelemetryResponseDTO.class
        );

        return response.getBody();
    }
}