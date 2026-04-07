package com.agms.automation_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "automation_logs")
@Data
public class AutomationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneId;

    private double temperature;

    private String action;

    private LocalDateTime timestamp;
}