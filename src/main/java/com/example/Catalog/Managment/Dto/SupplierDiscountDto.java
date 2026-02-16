package com.example.Catalog.Managment.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupplierDiscountDto
{
    @NotNull
    private BigDecimal discountPercent;
    @NotNull
    private int days;
}
