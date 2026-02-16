package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.Request.OrderRequestdto;
import com.example.Catalog.Managment.Dto.Response.OrderResponsedto;
import com.example.Catalog.Managment.Response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService
{
    ResponseEntity<ApiResponse<OrderResponsedto>> placeOrder(OrderRequestdto orderRequestdto);
    ResponseEntity<ApiResponse<OrderResponsedto>> getById(Long id);
    ResponseEntity<ApiResponse<OrderResponsedto>> confirmOrder(Long orderId);
    ResponseEntity<ApiResponse<OrderResponsedto>> cancelOrder(Long orderId);
    ResponseEntity<ApiResponse<OrderResponsedto>> requestReturn(Long id);
    ResponseEntity<ApiResponse<OrderResponsedto>> approveReturn(Long id);
    ResponseEntity<ApiResponse<OrderResponsedto>> completeReturn(Long id);
}
