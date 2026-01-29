package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.CustomerDto;
import com.example.Catalog.Managment.Entity.Customer;
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
    public ResponseEntity<CustomerDto> create (@Valid @RequestBody CustomerDto customerDto)
    {
        CustomerDto created = customerService.create(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping("/{id}")
        public ResponseEntity<CustomerDto> getById(@PathVariable int id)
    {
        CustomerDto getCustomer = customerService.getById(id);
        return getCustomer!= null
                ? ResponseEntity.ok(getCustomer)
                : ResponseEntity.badRequest().build();
    }
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll()
    {
        return ResponseEntity.ok(customerService.getAll());
    }


}
