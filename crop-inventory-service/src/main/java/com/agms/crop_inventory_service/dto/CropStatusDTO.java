package com.agms.crop_inventory_service.dto;

import com.agms.crop_inventory_service.entity.Crop.CropStatus;

public class CropStatusDTO {

    private CropStatus status;

    public CropStatusDTO() {}

    public CropStatusDTO(CropStatus status) {
        this.status = status;
    }

    public CropStatus getStatus() { return status; }
    public void setStatus(CropStatus status) { this.status = status; }
}