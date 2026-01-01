package com.cts.inventory_service.repository;

import com.cts.inventory_service.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Custom query to find stock by productId
    Optional<Inventory> findByProductId(Long productId);
}