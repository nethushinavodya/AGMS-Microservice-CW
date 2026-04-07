package com.agms.automation_service.service.impl;

import com.agms.automation_service.client.ZoneServiceClient;
import com.agms.automation_service.dto.AutomationLogDTO;
import com.agms.automation_service.dto.SensorDataDTO;
import com.agms.automation_service.dto.ZoneDTO;
import com.agms.automation_service.entity.AutomationLog;
import com.agms.automation_service.repository.AutomationLogRepository;
import com.agms.automation_service.service.AutomationService;
import com.agms.automation_service.util.RuleEngineUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutomationServiceImpl implements AutomationService {

    private final ZoneServiceClient zoneServiceClient;
    private final AutomationLogRepository automationLogRepository;

    @Override
    public String processSensorData(SensorDataDTO sensorDataDTO) {

        ZoneDTO zone = zoneServiceClient.getZoneById(sensorDataDTO.getZoneId());

        String action = RuleEngineUtil.checkTemperature(
                sensorDataDTO.getTemperature(),
                zone.getMinTemp(),
                zone.getMaxTemp()
        );

        AutomationLog log = new AutomationLog();
        log.setZoneId(sensorDataDTO.getZoneId());
        log.setTemperature(sensorDataDTO.getTemperature());
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());

        automationLogRepository.save(log);

        return action;
    }

    @Override
    public List<AutomationLogDTO> getAllLogs() {

        List<AutomationLog> logs = automationLogRepository.findAll();

        return logs.stream().map(log -> {

            AutomationLogDTO dto = new AutomationLogDTO();
            dto.setId(log.getId());
            dto.setZoneId(log.getZoneId());
            dto.setTemperature(log.getTemperature());
            dto.setAction(log.getAction());
            dto.setTimestamp(log.getTimestamp().toString());

            return dto;

        }).toList();
    }
}