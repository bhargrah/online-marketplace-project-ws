package com.bhargrah.orderservice.service;

import com.bhargrah.orderservice.dto.InventoryResponse;
import com.bhargrah.orderservice.dto.OrderLineItemsDTO;
import com.bhargrah.orderservice.dto.OrderRequest;
import com.bhargrah.orderservice.model.Order;
import com.bhargrah.orderservice.model.OrderLineItems;
import com.bhargrah.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    private final WebClient.Builder webClientBuilder;

    private static final String INVENTORY_URL = "http://localhost:8083/api/inventory/all";
    private static final String INVENTORY_URL_EUREKA_ALL = "http://inventory-service/api/inventory/all";
    private static final String INVENTORY_URL_EUREKA = "http://inventory-service/api/inventory/{skuCode}";

    //public static Logger log = LoggerFactory.getLogger(OrderService.class);

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItems(orderLineItems);

        // Check and validate the products sku codes
        if(validateProductCode(order)) throw new IllegalArgumentException("Order not accepted , product not found ,please try again later");

        // Call inventory service , place order if product in stock
        boolean allProductsInStocks = isAllProductsInStocks(order);

        if (allProductsInStocks)  {
            orderRepository.save(order);
            return "Order Placed Successfully";
        }
        else throw new IllegalArgumentException("Product is not in stock ,please try again later");

    }

    private boolean validateProductCode(Order order) {
        return order.getOrderLineItems().stream()
                     .map(OrderLineItems::getSkuCode)
                     .map(this::fetchProductState)
                     .filter(b -> !b)
                     .toList()
                     .size() > 0;
    }

    private Boolean fetchProductState(String skuCode){
        return webClient.get()
                .uri(INVENTORY_URL_EUREKA , skuCode)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    private boolean isAllProductsInStocks(Order order) {

        List<String> skuCodeList = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        log.info("Sku Code List : {}",skuCodeList);

        InventoryResponse[] inventoryResponses =
                webClientBuilder.build().get()
//                        .uri(INVENTORY_URL, uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodeList).build())
                        .uri(INVENTORY_URL_EUREKA_ALL, uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodeList).build())
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
