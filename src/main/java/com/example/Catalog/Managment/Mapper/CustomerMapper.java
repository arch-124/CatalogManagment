package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.CustomerDto;
import com.example.Catalog.Managment.Entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper
{

    CustomerDto toDto(Customer customer);

    Customer toEntity(CustomerDto customerDto);


}
