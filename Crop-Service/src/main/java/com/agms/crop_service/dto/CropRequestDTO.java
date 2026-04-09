package com.agms.crop_service.dto;

import lombok.Data;

@Data
public class CropRequestDTO {

    private String cropName;
    private String batchNumber;
    private String zoneId;
    private int quantity;
}