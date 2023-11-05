package com.bhargrah.orderservice.controller;

import com.bhargrah.orderservice.dto.OrderRequest;
import com.bhargrah.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@CircuitBreaker(name="inventory-service-ms" , fallbackMethod = "cannotPlaceOrder")
@TimeLimiter(name="inventory-service-ms")
@Retry(name="inventory-service-ms")
@RequestMapping("/api/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> cannotPlaceOrder(OrderRequest orderRequest , RuntimeException exception){
        log.error("Fallback strategy kicked in for order service");
        return CompletableFuture.supplyAsync(() -> "Order can't be placed due to network issue");
    }

}
