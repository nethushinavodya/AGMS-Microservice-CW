package com.agms.automation_service.repo;

import com.agms.automation_service.entity.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomationLogRepository extends JpaRepository<AutomationLog, Long> {
}