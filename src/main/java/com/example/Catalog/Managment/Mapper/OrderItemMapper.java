package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.OrderItemsDto;
import com.example.Catalog.Managment.Entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface OrderItemMapper
{
    @Mapping(source = "product.id", target = "productId")
        OrderItemsDto toDto(OrderItem item);
        List<OrderItemsDto> toDtoList(List<OrderItem> items);
}
