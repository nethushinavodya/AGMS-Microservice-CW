package com.agms.zone_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "sensor-service")
public interface SensorServiceClient {

    @PostMapping("/api/sensors/register")
    String registerDevice();
}