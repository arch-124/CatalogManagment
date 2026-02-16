package com.example.Catalog.Managment.Dto.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestdto
{
    @NotNull
    private Long customerId;


    @NotNull
    private List<OrderItemsRequestdto> items;

}
