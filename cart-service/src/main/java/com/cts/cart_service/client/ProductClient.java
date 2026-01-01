package com.cts.cart_service.client;

import com.cts.cart_service.dtos.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE") // This matches the spring.application.name
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id);
}