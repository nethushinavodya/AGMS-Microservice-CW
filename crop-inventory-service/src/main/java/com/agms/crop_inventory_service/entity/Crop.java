package com.agms.crop_inventory_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "crops")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity; // Number of plants in the batch

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    private LocalDateTime createdAt;

    public Crop() {}

    public Crop(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.status = CropStatus.SEEDLING;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public CropStatus getStatus() { return status; }
    public void setStatus(CropStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public enum CropStatus {
        SEEDLING, VEGETATIVE, HARVESTED
    }
}