package com.example.Catalog.Managment.Dto.Response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponsedto
{
    private Integer orderId;
    private String status;
    private BigDecimal totalPrice;
    private LocalDate orderDate;
    private List<OrderItemsResponsedto> orderItems;


}
