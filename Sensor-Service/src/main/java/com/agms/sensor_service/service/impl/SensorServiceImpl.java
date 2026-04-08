package com.agms.sensor_service.service.impl;

import com.agms.sensor_service.client.AutomationServiceClient;
import com.agms.sensor_service.client.ExternalIoTClient;
import com.agms.sensor_service.dto.AutomationRequestDTO;
import com.agms.sensor_service.dto.TelemetryResponseDTO;
import com.agms.sensor_service.service.SensorService;
import com.agms.sensor_service.util.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final ExternalIoTClient externalIoTClient;
    private final AutomationServiceClient automationServiceClient;
    private final TokenManager tokenManager;

    private TelemetryResponseDTO latestData;

    private final String DEVICE_ID = "b751b8c9-644a-484c-ba3f-be63f9b27ad0";

    @Scheduled(fixedRate = 10000)
    @Override
    public void fetchSensorData() {

        String token = tokenManager.getToken();

        TelemetryResponseDTO telemetry =
                externalIoTClient.getTelemetry(token, DEVICE_ID);

        latestData = telemetry;

        AutomationRequestDTO request = new AutomationRequestDTO();
        request.setZoneId(telemetry.getZoneId());
        request.setTemperature(telemetry.getValue().getTemperature());
        request.setHumidity(telemetry.getValue().getHumidity());

        automationServiceClient.sendToAutomation(request);

        System.out.println("Sensor Data Sent to Automation Service");
    }

    @Override
    public TelemetryResponseDTO getLatestData() {
        return latestData;
    }
}