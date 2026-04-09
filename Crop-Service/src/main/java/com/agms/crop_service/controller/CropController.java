package com.agms.crop_service.controller;

import com.agms.crop_service.dto.CropRequestDTO;
import com.agms.crop_service.dto.CropResponseDTO;
import com.agms.crop_service.dto.StatusUpdateDTO;
import com.agms.crop_service.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @PostMapping
    public CropResponseDTO saveCrop(@RequestBody CropRequestDTO dto) {
        return cropService.saveCrop(dto);
    }

    @PutMapping("/{id}/status")
    public CropResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateDTO dto) {

        return cropService.updateStatus(id, dto.getStatus());
    }

    @GetMapping
    public List<CropResponseDTO> getAllCrops() {
        return cropService.getAllCrops();
    }
}