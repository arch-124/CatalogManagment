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


    @Valid
    @NotEmpty
    private List<SkuCreateDto> skus;


    private Integer categoryId;
}
