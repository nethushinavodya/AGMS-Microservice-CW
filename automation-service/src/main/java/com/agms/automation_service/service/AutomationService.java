package com.agms.automation_service.service;

import com.agms.automation_service.dto.SensorDataDTO;
import com.agms.automation_service.dto.ZoneDTO;
import com.agms.automation_service.entity.AutomationLog;
import com.agms.automation_service.repo.AutomationLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AutomationService {

    private static final Logger log = LoggerFactory.getLogger(AutomationService.class);

    private final AutomationLogRepository repository;
    private final RestTemplate restTemplate;
    private final String zoneServiceUrl;

    public AutomationService(AutomationLogRepository repository, @Value("${zone.service.url:http://localhost:8081}") String zoneServiceUrl) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
        this.zoneServiceUrl = zoneServiceUrl;
    }

    public void processSensorData(SensorDataDTO data) {

        String zoneUrl = String.format("%s/api/zones/%s", zoneServiceUrl, data.getZoneId());
        ZoneDTO zone = null;
        try {
            zone = restTemplate.getForObject(zoneUrl, ZoneDTO.class);
        } catch (RestClientException e) {
            log.error("Failed to fetch zone {} from {}: {}", data.getZoneId(), zoneUrl, e.getMessage());
            // abort processing - can't decide action without zone thresholds
            return;
        }

        if (zone == null) {
            log.warn("Zone {} returned null from {}", data.getZoneId(), zoneUrl);
            return;
        }

        double minTemp = zone.getMinTemp();
        double maxTemp = zone.getMaxTemp();

        String action = "NONE";

        if (data.getTemperature() > maxTemp) {
            action = "TURN_FAN_ON";
        }

        if (data.getTemperature() < minTemp) {
            action = "TURN_HEATER_ON";
        }

        AutomationLog logEntry = new AutomationLog(
                data.getZoneId(),
                data.getTemperature(),
                data.getHumidity(),
                action
        );

        repository.save(logEntry);
    }

    public Object getLogs() {
        return repository.findAll();
    }
}