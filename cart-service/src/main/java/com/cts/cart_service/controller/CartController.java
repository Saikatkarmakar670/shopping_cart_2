package com.cts.cart_service.controller;

import com.cts.cart_service.dtos.CartDto;
import com.cts.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{userId}/{productId}/{quantity}")
    public ResponseEntity<CartDto> addItem(@PathVariable Long userId,
                                           @PathVariable Long productId,
                                           @PathVariable int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, productId, quantity));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<CartDto> removeItem(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        CartDto updatedCart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/update/{userId}/{productId}/{quantity}")
    public ResponseEntity<CartDto> updateQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @PathVariable int quantity) {

        CartDto updatedCart = cartService.updateItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }
}