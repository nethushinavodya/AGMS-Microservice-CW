package com.agms.zone_service.controller;

import com.agms.zone_service.dto.ZoneRequestDTO;
import com.agms.zone_service.dto.ZoneResponseDTO;
import com.agms.zone_service.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ZoneResponseDTO createZone(@RequestBody ZoneRequestDTO dto) {
        return zoneService.createZone(dto);
    }

    @GetMapping("/{id}")
    public ZoneResponseDTO getZone(@PathVariable Long id) {
        return zoneService.getZone(id);
    }

    @PutMapping("/{id}")
    public ZoneResponseDTO updateZone(
            @PathVariable Long id,
            @RequestBody ZoneRequestDTO dto) {

        return zoneService.updateZone(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
    }
}