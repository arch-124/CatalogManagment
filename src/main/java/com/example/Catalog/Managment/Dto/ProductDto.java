package com.example.Catalog.Managment.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto

{
    private Integer id;
    private String name;
    private Integer price;
    private Integer categoryId;
}
