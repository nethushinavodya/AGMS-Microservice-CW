package com.agms.crop_inventory_service.service;

import com.agms.crop_inventory_service.dto.CropStatusDTO;
import com.agms.crop_inventory_service.entity.Crop;
import com.agms.crop_inventory_service.repo.CropRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {

    private final CropRepository repository;

    public CropService(CropRepository repository) {
        this.repository = repository;
    }

    public Crop addCrop(Crop crop) {
        return repository.save(crop);
    }

    public Crop updateStatus(Long id, CropStatusDTO dto) {
        Crop crop = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));
        crop.setStatus(dto.getStatus());
        return repository.save(crop);
    }

    public List<Crop> getAllCrops() {
        return repository.findAll();
    }

    public Crop getCrop(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));
    }

    public void deleteCrop(Long id) {
        repository.deleteById(id);
    }
}