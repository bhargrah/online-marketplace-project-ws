package com.bhargrah.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDTO {

    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

}
