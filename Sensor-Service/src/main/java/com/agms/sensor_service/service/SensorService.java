package com.agms.sensor_service.service;

import com.agms.sensor_service.dto.TelemetryResponseDTO;

public interface SensorService {

    void fetchSensorData();

    TelemetryResponseDTO getLatestData();
}