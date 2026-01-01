package com.cts.inventory_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private Long productId;
    private Integer availableQuantity;

    @JsonProperty("isInStock")
    private boolean inStock;

}