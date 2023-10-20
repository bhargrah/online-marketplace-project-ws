package com.bhargrah.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Component
@Order(1) // Set the filter order
public class RequestResponseLoggingFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log the request information
        String requestMethod = exchange.getRequest().getMethod().toString();
        String requestURI = exchange.getRequest().getURI().toString();
        logger.info("Request - Method: {}, URI: {}", requestMethod, requestURI);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Log the response information
            int responseStatus = exchange.getResponse().getStatusCode().value();
            logger.info("Response - Status: {}", responseStatus);
        }));
    }
}