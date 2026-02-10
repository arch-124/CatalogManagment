package com.example.Catalog.Managment.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductcreateDto {

    @NotBlank
    private String name;

    // category created along with product
    @Valid
    @NotNull
    private CategoryDto category;

    // variants (SKUs)
    @Valid
    @NotEmpty
    private List<SkuCreateDto> skus;

    // optional if using existing category
    private Integer categoryId;
}
