package com.cts.cart_service.service;

import com.cts.cart_service.dtos.CartDto;

public interface CartService {
    CartDto addItemToCart(Long userId, Long productId, int quantity);
    CartDto getCartByUserId(Long userId);
    CartDto removeItemFromCart(Long userId, Long productId);
    CartDto updateItemQuantity(Long userId, Long productId, int newQuantity);
    void clearCart(Long userId);
}