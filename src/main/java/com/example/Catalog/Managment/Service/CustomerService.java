package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.CustomerDto;

import java.util.List;

public interface CustomerService
{
    CustomerDto create(CustomerDto customerDto);
    CustomerDto getById(int id);
    List<CustomerDto> getAll();
}
