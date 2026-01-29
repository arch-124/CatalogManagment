package com.example.Catalog.Managment.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto
{
    @NotNull
    @Min(value = 1,message = "product id should be positive")
    private Integer productId;
    @NotNull
    @Min(value = 1, message = "qunatity must be atleast 1.")
    private Integer quantity;


}
