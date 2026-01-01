package com.cts.cart_service.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import java.util.List;

@Data
@JsonPropertyOrder({ "id", "userId", "items", "totalAmount" })
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> items;
    private double totalAmount;
}