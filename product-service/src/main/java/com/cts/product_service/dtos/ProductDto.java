package com.cts.product_service.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","name","description","price","quantity"})
public class ProductDto {
    private Long id;

    @NotBlank(message="Product name is mandatory")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private double price;

    @Min(value = 0, message = "Quantity cannot be less than zero")
    private int quantity;
}