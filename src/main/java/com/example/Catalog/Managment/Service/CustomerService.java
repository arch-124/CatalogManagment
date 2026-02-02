package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.CustomerDto;
import com.example.Catalog.Managment.Response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService
{
    ResponseEntity<ApiResponse<CustomerDto>> create(CustomerDto customerDto);
    ResponseEntity<ApiResponse<CustomerDto>> getById(int id);
    ResponseEntity<ApiResponse<List<CustomerDto>>> getAll();
}
