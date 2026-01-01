package com.cts.cart_service.service;

import com.cts.cart_service.dtos.CartDto;
import com.cts.cart_service.dtos.ProductDto;
import com.cts.cart_service.entities.Cart;
import com.cts.cart_service.entities.CartItem;
import com.cts.cart_service.client.ProductClient;
import com.cts.cart_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;
    private final ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(Long userId, Long productId, int quantity) {
        // 1. Fetch Product details using Feign Client
        ProductDto product = productClient.getProductById(productId).getBody();
        if (product == null) throw new RuntimeException("Product not found");

        // 2. Get or create Cart for user
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return newCart;
        });

        // 3. Update existing item or add new one
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        updateTotal(cart);
        return modelMapper.map(cartRepository.save(cart), CartDto.class);
    }

    @Override
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        return modelMapper.map(cart, CartDto.class);
    }

//    @Override
//    public CartDto removeItemFromCart(Long userId, Long productId) {
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
//
//        updateTotal(cart);
//        return modelMapper.map(cartRepository.save(cart), CartDto.class);
//    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartRepository.delete(cart);
    }

    private void updateTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalAmount(total);
    }

    @Override
    public CartDto removeItemFromCart(Long userId, Long productId) {
        // 1. Find the cart for the user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        // 2. Remove the item with the matching product ID
        // removeIf is a clean way to filter out the specific product
        boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        if (!removed) {
            throw new RuntimeException("Product not found in cart");
        }

        // 3. Recalculate the total amount
        updateTotal(cart);

        // 4. Save and return updated DTO
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public CartDto updateItemQuantity(Long userId, Long productId, int newQuantity) {
        // 1. Find the cart for the user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        // 2. Find the specific item in the cart
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // 3. Update the quantity
        if (newQuantity <= 0) {
            // If quantity is 0 or less, we should just remove the item
            cart.getItems().remove(item);
        } else {
            item.setQuantity(newQuantity);
        }

        // 4. Recalculate total and save
        updateTotal(cart);
        Cart updatedCart = cartRepository.save(cart);

        return modelMapper.map(updatedCart, CartDto.class);
    }
}