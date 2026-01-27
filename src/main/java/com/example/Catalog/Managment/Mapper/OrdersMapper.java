package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.OrdersDto;
import com.example.Catalog.Managment.Entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrdersMapper {


    OrdersDto toDto(Orders order);
}
