package com.agms.automation_service.client;

import com.agms.automation_service.dto.ZoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ZONE-SERVICE")
public interface ZoneServiceClient {

    @GetMapping("/api/zones/{id}")
    ZoneDTO getZoneById(@PathVariable("id") String id);
}