package com.bhargrah.orderservice.service;

import com.bhargrah.orderservice.dto.InventoryResponse;
import com.bhargrah.orderservice.dto.OrderLineItemsDTO;
import com.bhargrah.orderservice.dto.OrderRequest;
import com.bhargrah.orderservice.model.Order;
import com.bhargrah.orderservice.model.OrderLineItems;
import com.bhargrah.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    private static final String INVENTORY_URL = "http://localhost:8083/api/inventory/all";

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItems(orderLineItems);

        // Call inventory service , place order if product in stock
        boolean allProductsInStocks = isAllProductsInStocks(order);

        if (allProductsInStocks)  orderRepository.save(order);
        else throw new IllegalArgumentException("Product is not in stock ,please try again later");

    }

    private boolean isAllProductsInStocks(Order order) {

        List<String> skuCodeList = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponses =
                webClient.get()
                        .uri(INVENTORY_URL, uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodeList).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        return Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;

    }
}
