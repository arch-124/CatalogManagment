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
    public ResponseEntity <ApiResponse<OrderResponsedto>> getById(@PathVariable int id)
    {
        return  orderService.getById(id);


    }
}
