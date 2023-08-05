package com.bhargrah.inventoryservice.controller;

import com.bhargrah.inventoryservice.dto.InventoryResponse;
import com.bhargrah.inventoryservice.model.Inventory;
import com.bhargrah.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode){
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isProductInStock(@RequestParam List<String> skuCodes){
        return inventoryService.isProductInStock(skuCodes);
    }

}
