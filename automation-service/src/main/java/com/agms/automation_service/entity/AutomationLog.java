package com.agms.automation_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "automation_logs")
public class AutomationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneId;

    private double temperature;

    private double humidity;

    private String action;

    private LocalDateTime createdAt;

    public AutomationLog() {}

    public AutomationLog(String zoneId, double temperature, double humidity, String action) {
        this.zoneId = zoneId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.action = action;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getZoneId() { return zoneId; }

    public void setZoneId(String zoneId) { this.zoneId = zoneId; }

    public double getTemperature() { return temperature; }

    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getHumidity() { return humidity; }

    public void setHumidity(double humidity) { this.humidity = humidity; }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}