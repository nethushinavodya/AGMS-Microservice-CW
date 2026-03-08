package com.agms.zone_service.dto;

public class DeviceRegistrationDTO {

    private String name;
    private String zoneId;

    public DeviceRegistrationDTO() {}

    public DeviceRegistrationDTO(String name, String zoneId) {
        this.name = name;
        this.zoneId = zoneId;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getZoneId() { return zoneId; }

    public void setZoneId(String zoneId) { this.zoneId = zoneId; }
}