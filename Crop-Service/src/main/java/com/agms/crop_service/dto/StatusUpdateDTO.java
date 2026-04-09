package com.agms.crop_service.dto;

import com.agms.crop_service.entity.CropStatus;
import lombok.Data;

@Data
public class StatusUpdateDTO {

    private CropStatus status;
}