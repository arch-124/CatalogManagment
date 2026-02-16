package com.example.Catalog.Managment.Dto;

import com.example.Catalog.Managment.Enum.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PODto {
    private Long orderId;
    private OrderStatus orderstatus;
}
