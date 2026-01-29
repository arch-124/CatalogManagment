package com.example.Catalog.Managment.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    @NotBlank
    @Min(value = 1, message = "price should be greater than 0")
    private Integer price;
   // private Integer categoryId;
}
