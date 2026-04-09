package com.agms.crop_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cropName;

    private String batchNumber;

    private String zoneId;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private CropStatus status;
}