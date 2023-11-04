package com.bhargrah.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(1) // Set the filter order
public class RequestResponseLoggingFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log the request information
        String id = exchange.getRequest().getId();
        String address = exchange.getRequest().getURI().toString();
        String method = exchange.getRequest().getMethod().toString();
        String headers = exchange.getRequest().getHeaders().toString();
        String params =  exchange.getRequest().getQueryParams().toString();

        logger.info("\n------------------------------ " +
                "\nID: {} " +
                "\nAddress: {} " +
                "\nHttp-Method: {} " +
                "\nHeaders: {} " +
                "\nPayload: {} " +
                "\n------------------------------" ,
                id,address,method,headers,params);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Log the response information
            String response_id = exchange.getRequest().getId();
            int responseStatus = exchange.getResponse().getStatusCode().value();
            logger.info("\n------------------------------ " +
                            "\nID: {} " +
                            "\nResponse-Code: {} " +
                            "\n------------------------------" ,
                    response_id,responseStatus);
        }));
    }}