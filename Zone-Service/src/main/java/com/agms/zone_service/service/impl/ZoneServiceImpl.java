package com.agms.zone_service.service.impl;

import com.agms.zone_service.client.SensorServiceClient;
import com.agms.zone_service.dto.ZoneRequestDTO;
import com.agms.zone_service.dto.ZoneResponseDTO;
import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.exception.InvalidTemperatureException;
import com.agms.zone_service.exception.ResourceNotFoundException;
import com.agms.zone_service.repository.ZoneRepository;
import com.agms.zone_service.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper mapper;
    private final SensorServiceClient sensorClient;

    @Override
    public ZoneResponseDTO createZone(ZoneRequestDTO dto) {

        if (dto.getMinTemp() >= dto.getMaxTemp()) {
            throw new InvalidTemperatureException("Min temp must be less than max temp");
        }

        String deviceId = sensorClient.registerDevice();

        Zone zone = Zone.builder()
                .zoneName(dto.getZoneName())
                .minTemp(dto.getMinTemp())
                .maxTemp(dto.getMaxTemp())
                .deviceId(deviceId)
                .build();

        return mapper.map(zoneRepository.save(zone), ZoneResponseDTO.class);
    }

    @Override
    public ZoneResponseDTO getZone(Long id) {

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        return mapper.map(zone, ZoneResponseDTO.class);
    }

    @Override
    public ZoneResponseDTO updateZone(Long id, ZoneRequestDTO dto) {

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        zone.setZoneName(dto.getZoneName());
        zone.setMinTemp(dto.getMinTemp());
        zone.setMaxTemp(dto.getMaxTemp());

        return mapper.map(zoneRepository.save(zone), ZoneResponseDTO.class);
    }

    @Override
    public void deleteZone(Long id) {

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        zoneRepository.delete(zone);
    }
}