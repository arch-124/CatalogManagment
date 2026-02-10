package com.example.Catalog.Managment.Dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemsRequestdto
{
    @NotNull
    @Min(value =1 )
    private Long skuId;

    @NotNull
    @Min(value =1 )
    private Integer quantity;
}
