package com.agms.zone_service.service;

import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.repo.ZoneRepository;
import com.agms.zone_service.dto.DeviceRegistrationDTO;
import com.agms.zone_service.feign.IoTIntegrationClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class ZoneService {

    private final ZoneRepository repository;
    private final IoTIntegrationClient iotClient;

    public ZoneService(ZoneRepository repository, IoTIntegrationClient iotClient) {
        this.repository = repository;
        this.iotClient = iotClient;
    }

    public Zone createZone(Zone zone, String bearerToken) {

        if (zone.getMinTemp() >= zone.getMaxTemp()) {
            throw new IllegalArgumentException("minTemp must be less than maxTemp");
        }

        // Save first to generate ID
        Zone saved = repository.save(zone);

        // Register device with IoT API
        DeviceRegistrationDTO dto = new DeviceRegistrationDTO(zone.getName(), saved.getId().toString());

        Map<String, Object> response = iotClient.registerDevice(bearerToken, dto);

        if (response.containsKey("deviceId")) {
            saved.setDeviceId(response.get("deviceId").toString());
            repository.save(saved);
        }

        return saved;
    }

    public Zone getZone(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found"));
    }

    public List<Zone> getAllZones() {
        return repository.findAll();
    }

    public Zone updateZone(Long id, Zone updatedZone) {
        Zone zone = getZone(id);
        zone.setMinTemp(updatedZone.getMinTemp());
        zone.setMaxTemp(updatedZone.getMaxTemp());
        zone.setName(updatedZone.getName());
        return repository.save(zone);
    }

    public void deleteZone(Long id) {
        repository.deleteById(id);
    }
}