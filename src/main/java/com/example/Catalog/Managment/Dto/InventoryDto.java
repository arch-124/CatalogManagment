package com.example.Catalog.Managment.Dto;

import com.example.Catalog.Managment.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class InventoryDto
{
    private Integer productId;
    private Integer id;
    private Integer quantity;

}
