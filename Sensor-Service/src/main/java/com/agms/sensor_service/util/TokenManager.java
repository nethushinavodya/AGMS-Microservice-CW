package com.agms.sensor_service.util;

import com.agms.sensor_service.client.ExternalIoTClient;
import com.agms.sensor_service.dto.AuthRequestDTO;
import com.agms.sensor_service.dto.AuthResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    private final ExternalIoTClient externalIoTClient;

    private String token;

    public TokenManager(ExternalIoTClient externalIoTClient) {
        this.externalIoTClient = externalIoTClient;
    }

    public String getToken() {

        if (token == null) {

            AuthRequestDTO request = new AuthRequestDTO();
            request.setUsername("username");
            request.setPassword("123456");

            AuthResponseDTO response = externalIoTClient.login(request);

            token = response.getAccessToken();
        }

        return token;
    }
}