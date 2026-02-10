package com.example.Catalog.Managment.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuDto {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    private Boolean active;

    @NotNull
    @Positive
    private Integer productId;

    @NotEmpty
    private Map<String, String> attributes;
}
