package com.agms.crop_inventory_service.controller;

import com.agms.crop_inventory_service.dto.CropStatusDTO;
import com.agms.crop_inventory_service.entity.Crop;
import com.agms.crop_inventory_service.service.CropService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @PostMapping
    public Crop addCrop(@RequestBody Crop crop) {
        return cropService.addCrop(crop);
    }

    @PutMapping("/{id}/status")
    public Crop updateStatus(@PathVariable Long id, @RequestBody CropStatusDTO dto) {
        return cropService.updateStatus(id, dto);
    }

    @GetMapping
    public List<Crop> getAllCrops() {
        return cropService.getAllCrops();
    }

    @GetMapping("/{id}")
    public Crop getCrop(@PathVariable Long id) {
        return cropService.getCrop(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return "Deleted successfully";
    }
}