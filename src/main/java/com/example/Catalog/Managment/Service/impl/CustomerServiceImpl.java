package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.CustomerDto;
import com.example.Catalog.Managment.Entity.Customer;
import com.example.Catalog.Managment.Mapper.CustomerMapper;
import com.example.Catalog.Managment.Repository.CustomerRepository;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<CustomerDto>> create(CustomerDto customerDto)
    {
        try
        {
            Customer customer = customerMapper.toEntity(customerDto);
            Customer saved = customerRepository.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            HttpStatus.CREATED.value(),
                            "Customer created successfully",
                            customerMapper.toDto(saved))
            );
        }


        catch (Exception e)
        {
            log.error("failed to create customer",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create Customer",
                            null
                    ));

        }
    }

    @Override
    public ResponseEntity<ApiResponse<CustomerDto>> getById(Long id)
    {
        try
        {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("customer not found with given id"));
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Customer fetched successfully",
                    customerMapper.toDto(customer)
            ));

        }
        catch (Exception e)
        {
            log.error("failed to get customer",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.NOT_FOUND.value(),
                            "Customer not found",
                            null
                    ));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getAll()
    {
        try
        {
             List<CustomerDto> customers = customerRepository.findAll()
                    .stream()
                    .map(customerMapper::toDto)
                    .toList();
             return ResponseEntity.ok(
                     new ApiResponse(
                             true,
                             HttpStatus.OK.value(),
                             "Customers fetched successfully",
                             customers
                     )
             );
        }
        catch (Exception e)
        {
            log.error("failed to fetch customers",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to fetch Customers",
                            null
                    ));

        }
    }
}
