package com.agms.api_gateway.config;

import com.agms.api_gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {

        return builder.routes()

                .route("zone-service", r -> r
                        .path("/api/zones/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://zone-service"))

                .route("sensor-service", r -> r
                        .path("/api/sensors/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://sensor-service"))

                .route("automation-service", r -> r
                        .path("/api/automation/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://automation-service"))

                .route("crop-service", r -> r
                        .path("/api/crops/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://crop-service"))

                .build();
    }
}