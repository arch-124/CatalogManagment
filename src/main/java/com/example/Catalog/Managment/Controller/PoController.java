package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Enum.OrderStatus;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.POService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/po")
@RequiredArgsConstructor
public class PoController {

    private final POService poService;


    @PostMapping("/create/{orderId}")
    public ResponseEntity<ApiResponse<String>> createPO(@PathVariable Long orderId, @RequestParam OrderStatus status) {

        return poService.createPO(orderId, status);

    }
}
