package com.agms.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> chain.filter(exchange);
    }
}