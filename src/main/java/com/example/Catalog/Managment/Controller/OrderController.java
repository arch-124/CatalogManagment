package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.OrdersDto;
import com.example.Catalog.Managment.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrdersDto> placeOrder(@RequestBody OrdersDto dto) {
       OrdersDto createdorder = orderService.placeOrder(dto);
       return ResponseEntity.status(HttpStatus .CREATED).body(createdorder);
    }

    @GetMapping("/{id}")
    public ResponseEntity <OrdersDto> getById(@PathVariable int id) {
        OrdersDto orders =  orderService.getById(id);
        return ResponseEntity.ok(orders);
    }
}
