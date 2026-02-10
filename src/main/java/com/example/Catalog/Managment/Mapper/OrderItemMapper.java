package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.Response.OrderItemsResponsedto;
import com.example.Catalog.Managment.Entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "sku.id", target = "skuId")
    @Mapping(source = "sku.product.id", target = "productId")
    @Mapping(source = "sku.product.name", target = "productName")
    @Mapping(source = "priceAtPurchase", target = "price")
    @Mapping(source = "totalPrice", target = "subtotal")
    OrderItemsResponsedto toDto(OrderItem item);

    List<OrderItemsResponsedto> toDtoList(List<OrderItem> items);
}
