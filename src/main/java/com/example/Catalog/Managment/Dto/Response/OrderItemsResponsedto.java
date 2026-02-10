package com.example.Catalog.Managment.Dto.Response;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsResponsedto {

    @NotNull(message = "SKU id cannot be null")
    @Positive(message = "SKU id must be positive")
    private Long skuId;

    @NotNull(message = "Product id cannot be null")
    @Positive(message = "Product id must be positive")
    private Integer productId;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotNull(message = "Quantity required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Price required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Subtotal required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Subtotal must be greater than 0")
    private BigDecimal subtotal;
}