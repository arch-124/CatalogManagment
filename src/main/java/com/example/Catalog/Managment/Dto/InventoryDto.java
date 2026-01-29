package com.example.Catalog.Managment.Dto;

import com.example.Catalog.Managment.Entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class InventoryDto
{
    @NotNull
    @Min(value = 1, message = "product id should be positive")
    private Integer productId;
    private Integer id;
    @NotNull
    @Min(value = 0,message = "quantity can not be negative")
    private Integer quantity;

}
