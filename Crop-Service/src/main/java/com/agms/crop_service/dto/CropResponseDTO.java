package com.agms.crop_service.dto;

import com.agms.crop_service.entity.CropStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CropResponseDTO {

    private Long id;
    private String cropName;
    private String batchNumber;
    private String zoneId;
    private int quantity;
    private CropStatus status;
}