package com.example.Catalog.Managment.Dto.Response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponsedto {

    @NotNull
    private Long orderId;

    @NotBlank
    private String status;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private LocalDate orderDate;

    @NotEmpty
    @Valid
    private List<OrderItemsResponsedto> orderItems;
}
