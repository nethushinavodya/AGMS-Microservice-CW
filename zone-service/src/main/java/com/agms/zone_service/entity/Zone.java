package com.agms.zone_service.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "zones")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double minTemp;

    private double maxTemp;

    private String deviceId; // Assigned after IoT registration

    private LocalDateTime createdAt;

    public Zone() {}

    public Zone(String name, double minTemp, double maxTemp) {
        this.name = name;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.createdAt = LocalDateTime.now();
    }

    public void setName(String name) { this.name = name; }

    public void setMinTemp(double minTemp) { this.minTemp = minTemp; }

    public void setMaxTemp(double maxTemp) { this.maxTemp = maxTemp; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    // Explicit getters (ensure compilation even if Lombok isn't processed)
    public Long getId() { return id; }

    public String getName() { return name; }

    public double getMinTemp() { return minTemp; }

    public double getMaxTemp() { return maxTemp; }

    public String getDeviceId() { return deviceId; }

}