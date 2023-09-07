package com.bhargrah.inventoryservice.service;

import com.bhargrah.inventoryservice.dto.InventoryResponse;
import com.bhargrah.inventoryservice.model.Inventory;
import com.bhargrah.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public static Logger log = LoggerFactory.getLogger(InventoryService.class);

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isProductInStock(List<String> skuCodeList){
        return
                inventoryRepository.findBySkuCodeIn(skuCodeList).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }




}
