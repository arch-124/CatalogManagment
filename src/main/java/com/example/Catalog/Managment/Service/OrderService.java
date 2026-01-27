package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.OrdersDto;
import com.example.Catalog.Managment.Entity.Orders;
import org.hibernate.query.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

public interface OrderService
{
    OrdersDto placeOrder(OrdersDto ordersDto);
    OrdersDto getById(int id);
}
