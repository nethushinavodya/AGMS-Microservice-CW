package com.agms.sensor_telemetry_service.service;

import com.agms.sensor_telemetry_service.dto.SensorDataDTO;
import com.agms.sensor_telemetry_service.feign.AutomationClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class SensorService {

    private static final Logger log = LoggerFactory.getLogger(SensorService.class);

    private final WebClient webClient;
    private final AutomationClient automationClient;

    private String bearerToken;
    private SensorDataDTO lastReading;

    @Value("${iot.url}")
    private String iotUrl;

    @Value("${iot.username}")
    private String username;

    @Value("${iot.password}")
    private String password;

    public SensorService(AutomationClient automationClient) {
        this.automationClient = automationClient;
        this.webClient = WebClient.builder().build(); // baseUrl set dynamically later
    }

    @PostConstruct
    public void init() {
        try {
            loginToExternalAPI();
        } catch (Exception e) {
            log.warn("Initial login failed: {}", e.getMessage());
        }
    }

    /**
     * Login to IoT API and set bearer token
     */
    private void loginToExternalAPI() {
        Map<String, String> credentials = Map.of(
                "username", username,
                "password", password
        );

        Map<String, Object> response;
        try {
            response = WebClient.builder()
                    .baseUrl(iotUrl)
                    .build()
                    .post()
                    .uri("/auth/login")
                    .bodyValue(credentials)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new IllegalStateException("Login failed with status " + e.getStatusCode() + ": " + e.getResponseBodyAsString());
        }

        if (response == null || !response.containsKey("accessToken")) {
            throw new IllegalStateException("Login failed: no accessToken in response " + response);
        }

        this.bearerToken = "Bearer " + response.get("accessToken");
        log.info("Successfully logged into IoT API. Token length: {}", bearerToken.length());
    }

    /**
     * Fetch all devices
     */
    private List<Map> fetchDevices() {
        ensureLoggedIn();
        try {
            return WebClient.builder()
                    .baseUrl(iotUrl)
                    .build()
                    .get()
                    .uri("/devices")
                    .header("Authorization", bearerToken)
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.warn("Fetch devices failed: {}, trying to relogin", e.getStatusCode());
            bearerToken = null; // force relogin next time
            return null;
        }
    }

    /**
     * Fetch telemetry for a device
     */
    private Map fetchTelemetry(String deviceId) {
        ensureLoggedIn();
        try {
            return WebClient.builder()
                    .baseUrl(iotUrl)
                    .build()
                    .get()
                    .uri("/devices/telemetry/{id}", deviceId)
                    .header("Authorization", bearerToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.warn("Fetch telemetry failed for device {}: {}", deviceId, e.getStatusCode());
            bearerToken = null;
            return null;
        }
    }

    /**
     * Ensure we are logged in before fetching
     */
    private void ensureLoggedIn() {
        if (bearerToken == null) {
            try {
                loginToExternalAPI();
            } catch (Exception e) {
                log.warn("Login failed, cannot fetch data: {}", e.getMessage());
            }
        }
    }

    /**
     * Scheduled task: every 10 seconds
     */
    @Scheduled(fixedRate = 10000)
    public void fetchAndPushSensorData() {
        List<Map> devices = fetchDevices();
        if (devices == null || devices.isEmpty()) {
            log.warn("No devices found or fetch failed");
            return;
        }

        for (Map device : devices) {
            Object deviceIdObj = device.get("deviceId");
            Object zoneIdObj = device.get("zoneId");
            if (deviceIdObj == null || zoneIdObj == null) continue;

            String deviceId = deviceIdObj.toString();
            String zoneId = zoneIdObj.toString();

            Map telemetry = fetchTelemetry(deviceId);
            if (telemetry == null) continue;

            Object valueObj = telemetry.get("value");
            if (!(valueObj instanceof Map)) continue;
            Map value = (Map) valueObj;

            Object tempObj = value.get("temperature");
            Object humObj = value.get("humidity");
            if (!(tempObj instanceof Number) || !(humObj instanceof Number)) continue;

            double temperature = ((Number) tempObj).doubleValue();
            double humidity = ((Number) humObj).doubleValue();

            SensorDataDTO data = new SensorDataDTO();
            data.setDeviceId(deviceId);
            data.setZoneId(zoneId);
            data.setTemperature(temperature);
            data.setHumidity(humidity);

            this.lastReading = data;

            try {
                automationClient.sendSensorData(data);
            } catch (Exception e) {
                log.warn("Failed to send sensor data to automation service: {}", e.getMessage());
            }
        }
    }

    public SensorDataDTO getLastReading() {
        return lastReading;
    }
}