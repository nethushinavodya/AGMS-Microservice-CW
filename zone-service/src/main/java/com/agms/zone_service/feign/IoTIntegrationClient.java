package com.agms.zone_service.feign;

import com.agms.zone_service.dto.DeviceRegistrationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "iot-integration", url = "http://104.211.95.241:8080/api")
public interface IoTIntegrationClient {

    @PostMapping("/devices")
    Map<String, Object> registerDevice(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody DeviceRegistrationDTO device
    );
}