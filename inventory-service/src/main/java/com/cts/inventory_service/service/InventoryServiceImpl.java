package com.cts.inventory_service.service.impl;

import com.cts.inventory_service.dtos.InventoryResponse;
import com.cts.inventory_service.entities.Inventory;
import com.cts.inventory_service.repository.InventoryRepository;
import com.cts.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse checkStock(Long productId, Integer quantity) {
        // Find product in inventory or throw exception if not tracked
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory record not found for Product ID: " + productId));

        // Logic: Is requested quantity <= available stock?
        boolean inStock = inventory.getQuantity() >= quantity;

        return new InventoryResponse(
                productId,
                inventory.getQuantity(),
                inStock

        );
    }
}