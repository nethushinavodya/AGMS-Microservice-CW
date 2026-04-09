package com.agms.zone_service.service;

import com.agms.zone_service.dto.ZoneRequestDTO;
import com.agms.zone_service.dto.ZoneResponseDTO;

public interface ZoneService {

    ZoneResponseDTO createZone(ZoneRequestDTO dto);

    ZoneResponseDTO getZone(Long id);

    ZoneResponseDTO updateZone(Long id, ZoneRequestDTO dto);

    void deleteZone(Long id);
}