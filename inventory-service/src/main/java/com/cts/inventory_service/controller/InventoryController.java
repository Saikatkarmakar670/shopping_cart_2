package com.cts.inventory_service.controller;

import com.cts.inventory_service.dtos.InventoryResponse;
import com.cts.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Endpoint: GET http://localhost:9088/api/inventory/check/1/5
    @GetMapping("/check/{productId}/{quantity}")
    public ResponseEntity<InventoryResponse> checkStock(
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        InventoryResponse response = inventoryService.checkStock(productId, quantity);
        return ResponseEntity.ok(response);
    }
}