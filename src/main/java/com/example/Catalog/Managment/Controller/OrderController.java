package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.Request.OrderRequestdto;
import com.example.Catalog.Managment.Dto.Response.OrderResponsedto;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponsedto>> placeOrder(@Valid @RequestBody OrderRequestdto requestdto)
    {
       return orderService.placeOrder(requestdto);


    }

    @GetMapping("/{id}")
    public ResponseEntity <ApiResponse<OrderResponsedto>> getById(@PathVariable Long id)
    {
        return  orderService.getById(id);


    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<ApiResponse<OrderResponsedto>> confirmOrder(@PathVariable Long orderId)
    {
        return orderService.confirmOrder(orderId);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponsedto>> cancelOrder(@PathVariable Long orderId)
    {
        return orderService.cancelOrder(orderId);
    }

    @PostMapping("/{orderId}/return-request")
    public ResponseEntity<ApiResponse<OrderResponsedto>> requestReturn(@PathVariable Long orderId)
    {
        return orderService.requestReturn(orderId);
    }
    @PostMapping("/{orderId}/approve-request")
    public ResponseEntity<ApiResponse<OrderResponsedto>> approveReturn(@PathVariable Long orderId)
    {
        return orderService.approveReturn(orderId);
    }
    @PostMapping("/{orderId}/complete-request")
    public ResponseEntity<ApiResponse<OrderResponsedto>> completeReturn(@PathVariable Long orderId)
    {
        return orderService.completeReturn(orderId);
    }

}
