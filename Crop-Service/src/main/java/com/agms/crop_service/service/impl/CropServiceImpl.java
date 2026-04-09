package com.agms.crop_service.service.impl;

import com.agms.crop_service.dto.CropRequestDTO;
import com.agms.crop_service.dto.CropResponseDTO;
import com.agms.crop_service.entity.Crop;
import com.agms.crop_service.entity.CropStatus;
import com.agms.crop_service.exception.ResourceNotFoundException;
import com.agms.crop_service.repository.CropRepository;
import com.agms.crop_service.service.CropService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;
    private final ModelMapper mapper;

    @Override
    public CropResponseDTO saveCrop(CropRequestDTO dto) {

        Crop crop = Crop.builder()
                .cropName(dto.getCropName())
                .batchNumber(dto.getBatchNumber())
                .zoneId(dto.getZoneId())
                .quantity(dto.getQuantity())
                .status(CropStatus.SEEDLING)
                .build();

        Crop saved = cropRepository.save(crop);

        return mapper.map(saved, CropResponseDTO.class);
    }

    @Override
    public CropResponseDTO updateStatus(Long id, CropStatus status) {

        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found"));

        crop.setStatus(status);

        return mapper.map(cropRepository.save(crop), CropResponseDTO.class);
    }

    @Override
    public List<CropResponseDTO> getAllCrops() {

        return cropRepository.findAll()
                .stream()
                .map(crop -> mapper.map(crop, CropResponseDTO.class))
                .collect(Collectors.toList());
    }
}