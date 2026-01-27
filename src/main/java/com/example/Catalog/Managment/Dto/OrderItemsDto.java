package com.example.Catalog.Managment.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto
{
    private Integer productId;
    private Integer quantity;


}
