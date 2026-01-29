package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.CustomerDto;
import com.example.Catalog.Managment.Entity.Customer;
import com.example.Catalog.Managment.Mapper.CustomerMapper;
import com.example.Catalog.Managment.Repository.CustomerRepository;
import com.example.Catalog.Managment.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService
{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public CustomerDto create(CustomerDto customerDto)
    {
        try
        {
            Customer customer = customerMapper.toEntity(customerDto);
            Customer saved = customerRepository.save(customer);
            return customerMapper.toDto(saved);
        }
        catch (Exception e)
        {
            log.error("failed to create customer",e);
            return null;

        }
    }

    @Override
    public CustomerDto getById(int id)
    {
        try
        {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("customer not found with given id"));
            return customerMapper.toDto(customer);
        }
        catch (Exception e)
        {
            log.error("failed to get customer",e);
            return null;
        }

    }

    @Override
    public List<CustomerDto> getAll()
    {
        try
        {
            return customerRepository.findAll()
                    .stream()
                    .map(customerMapper::toDto)
                    .toList();
        }
        catch (Exception e)
        {
            log.error("failed to fetch customers",e);
            return null;

        }
    }
}
