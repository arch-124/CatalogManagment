package com.example.Catalog.Managment.Dto;

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
    private String orderstatus;
//    private Double totalamount;
    private List<OrderItemsDto> orderItems;

}
