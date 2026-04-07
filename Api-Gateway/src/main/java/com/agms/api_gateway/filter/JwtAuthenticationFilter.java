package com.agms.api_gateway.filter;

import com.agms.api_gateway.constant.SecurityConstants;
import com.agms.api_gateway.exception.UnauthorizedException;
import com.agms.api_gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        String authHeader = request.getHeaders()
                .getFirst(SecurityConstants.HEADER);

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.PREFIX)) {
            throw new UnauthorizedException("Missing Authorization Header");
        }

        String token = authHeader.replace(SecurityConstants.PREFIX, "");

        if (!JwtUtil.validateToken(token)) {
            throw new UnauthorizedException("Invalid Token");
        }

        return chain.filter(exchange);
    }
}