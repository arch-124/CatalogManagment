package com.example.Catalog.Managment.Dto.Response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsResponsedto
{
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
