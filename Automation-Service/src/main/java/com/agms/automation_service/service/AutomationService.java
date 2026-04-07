package com.agms.automation_service.service;

import com.agms.automation_service.dto.AutomationLogDTO;
import com.agms.automation_service.dto.SensorDataDTO;
import com.agms.automation_service.entity.AutomationLog;

import java.util.List;

public interface AutomationService {

    String processSensorData(SensorDataDTO sensorDataDTO);

    List<AutomationLogDTO> getAllLogs();
}