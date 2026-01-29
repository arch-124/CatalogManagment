package com.example.Catalog.Managment.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDto
{

    private Integer id;
//    private String ordernumber;
//    private LocalDate orderdate;

    @NotBlank
    private String orderstatus;
//    private Double totalamount;
    @NotEmpty
    @Valid
    private List<OrderItemsDto> orderItems;

}
