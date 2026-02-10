package com.example.Catalog.Managment.Dto;

import com.example.Catalog.Managment.Entity.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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


    private Integer categoryId;


   private String categoryName;


}
