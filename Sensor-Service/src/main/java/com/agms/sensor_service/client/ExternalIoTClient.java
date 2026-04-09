package com.agms.sensor_service.client;

import com.agms.sensor_service.dto.AuthRequestDTO;
import com.agms.sensor_service.dto.AuthResponseDTO;
import com.agms.sensor_service.dto.TelemetryResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalIoTClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalIoTClient.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ExternalIoTClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        try {
            HttpEntity<AuthRequestDTO> entity = new HttpEntity<>(request);

            ResponseEntity<AuthResponseDTO> response = restTemplate.exchange(
                    baseUrl + "/auth/login",
                    HttpMethod.POST,
                    entity,
                    AuthResponseDTO.class
            );

            return response.getBody();
        } catch (Exception ex) {
            log.warn("ExternalIoTClient.login failed: {}", ex.getMessage());
            return null;
        }
    }

    public TelemetryResponseDTO getTelemetry(String token, String deviceId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<TelemetryResponseDTO> response = restTemplate.exchange(
                    baseUrl + "/devices/telemetry/" + deviceId,
                    HttpMethod.GET,
                    entity,
                    TelemetryResponseDTO.class
            );

            return response.getBody();
        } catch (Exception ex) {
            log.warn("ExternalIoTClient.getTelemetry failed: {}", ex.getMessage());
            return null;
        }
    }
}