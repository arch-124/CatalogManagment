package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.OrdersDto;
import com.example.Catalog.Managment.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrdersDto placeOrder(@RequestBody OrdersDto dto) {
        return orderService.placeOrder(dto);
    }

    @GetMapping("/{id}")
    public OrdersDto getById(@PathVariable int id) {
        return orderService.getById(id);
    }
}
