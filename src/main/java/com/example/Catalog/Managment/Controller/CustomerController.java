package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.CustomerDto;
import com.example.Catalog.Managment.Entity.Customer;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")

public class CustomerController
{
    private final CustomerService customerService;
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDto>> create (@Valid @RequestBody CustomerDto customerDto)
    {
        return customerService.create(customerDto);

    }
    @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<CustomerDto>> getById(@PathVariable Long id)
    {
        return customerService.getById(id);

    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getAll()
    {
        return customerService.getAll();
    }


}
