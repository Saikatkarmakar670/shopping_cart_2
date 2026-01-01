package com.cts.cart_service.dtos;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName; // We will get this from Product Service
    private int quantity;
    private double price;
}