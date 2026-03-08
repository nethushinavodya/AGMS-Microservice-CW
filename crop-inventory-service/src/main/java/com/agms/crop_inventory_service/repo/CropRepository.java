package com.agms.crop_inventory_service.repo;

import com.agms.crop_inventory_service.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long> {
}