package com.agms.zone_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneRequestDTO {

    private String zoneName;
    private double minTemp;
    private double maxTemp;
}