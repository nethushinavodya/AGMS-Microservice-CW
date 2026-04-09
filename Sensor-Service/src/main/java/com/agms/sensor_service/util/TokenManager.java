package com.agms.sensor_service.util;

import com.agms.sensor_service.client.ExternalIoTClient;
import com.agms.sensor_service.dto.AuthRequestDTO;
import com.agms.sensor_service.dto.AuthResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    private static final Logger log = LoggerFactory.getLogger(TokenManager.class);

    private final ExternalIoTClient externalIoTClient;

    @Value("${agms.external-iot.enabled:false}")
    private boolean externalIoTEnabled;

    @Value("${agms.external-iot.username:username}")
    private String username;

    @Value("${agms.external-iot.password:123456}")
    private String password;

    private String token;
    private volatile long nextAllowedAttemptEpochMs = 0L;

    public TokenManager(ExternalIoTClient externalIoTClient) {
        this.externalIoTClient = externalIoTClient;
    }

    public synchronized String getToken() {

        if (!externalIoTEnabled) {
            return null;
        }

        if (token != null) {
            return token;
        }

        long now = System.currentTimeMillis();
        if (now < nextAllowedAttemptEpochMs) {
            return null;
        }

        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername(username);
        request.setPassword(password);

        AuthResponseDTO response = null;
        int attempts = 0;
        while (attempts < 3 && response == null) {
            response = externalIoTClient.login(request);
            attempts++;
            if (response == null) {
                try {
                    Thread.sleep(1000L * attempts);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        if (response != null) {
            token = response.getAccessToken();
            log.info("External IoT token acquired");
            return token;
        }

        // Back off for 60s before trying again to prevent constant timeout spam
        nextAllowedAttemptEpochMs = System.currentTimeMillis() + 60_000L;
        log.warn("External IoT token unavailable; will retry after backoff");
        return null;
    }
}