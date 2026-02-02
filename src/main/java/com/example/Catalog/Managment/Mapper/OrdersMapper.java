package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.Response.OrderResponsedto;
import com.example.Catalog.Managment.Entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrdersMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "orderstatus", target = "status")
    @Mapping(source = "totalamount", target = "totalPrice")
    @Mapping(source = "orderdate", target = "orderDate")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponsedto toDto(Orders order);
}
