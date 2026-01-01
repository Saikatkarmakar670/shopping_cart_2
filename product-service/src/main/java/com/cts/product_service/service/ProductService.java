package com.cts.product_service.service;

import com.cts.product_service.dtos.ProductDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}