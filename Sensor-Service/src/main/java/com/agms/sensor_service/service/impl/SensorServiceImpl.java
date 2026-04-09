package com.agms.sensor_service.service.impl;

import com.agms.sensor_service.client.AutomationServiceClient;
import com.agms.sensor_service.client.ExternalIoTClient;
import com.agms.sensor_service.dto.AutomationRequestDTO;
import com.agms.sensor_service.dto.TelemetryResponseDTO;
import com.agms.sensor_service.service.SensorService;
import com.agms.sensor_service.util.TokenManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private static final Logger log = LoggerFactory.getLogger(SensorServiceImpl.class);

    private final ExternalIoTClient externalIoTClient;
    private final AutomationServiceClient automationServiceClient;
    private final TokenManager tokenManager;

    private TelemetryResponseDTO latestData;

    @Value("${agms.external-iot.device-id:b751b8c9-644a-484c-ba3f-be63f9b27ad0}")
    private String deviceId;

    @Scheduled(fixedRate = 10000)
    @Override
    public void fetchSensorData() {

        String token = tokenManager.getToken();

        if (token == null) {
            // When external IoT is disabled or temporarily unavailable, just skip.
            log.debug("Token unavailable - skipping telemetry fetch");
            return;
        }

        TelemetryResponseDTO telemetry = externalIoTClient.getTelemetry(token, deviceId);

        if (telemetry == null) {
            log.warn("Failed to fetch telemetry for device {}", deviceId);
            return;
        }

        latestData = telemetry;

        AutomationRequestDTO request = new AutomationRequestDTO();
        request.setZoneId(telemetry.getZoneId());
        request.setTemperature(telemetry.getValue().getTemperature());
        request.setHumidity(telemetry.getValue().getHumidity());

        try {
            automationServiceClient.sendToAutomation(request);
            log.info("Sensor Data Sent to Automation Service");
        } catch (Exception ex) {
            log.error("Failed to send data to automation service: {}", ex.getMessage());
        }
    }

    @Override
    public TelemetryResponseDTO getLatestData() {
        return latestData;
    }
}