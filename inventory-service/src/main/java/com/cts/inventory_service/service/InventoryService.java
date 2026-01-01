package com.cts.inventory_service.service;

import com.cts.inventory_service.dtos.InventoryResponse;

public interface InventoryService {
    InventoryResponse checkStock(Long productId, Integer quantity);
}