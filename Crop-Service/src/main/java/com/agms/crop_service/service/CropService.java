package com.agms.crop_service.service;

import com.agms.crop_service.dto.CropRequestDTO;
import com.agms.crop_service.dto.CropResponseDTO;
import com.agms.crop_service.entity.CropStatus;

import java.util.List;

public interface CropService {

    CropResponseDTO saveCrop(CropRequestDTO dto);

    CropResponseDTO updateStatus(Long id, CropStatus status);

    List<CropResponseDTO> getAllCrops();
}