package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper
{
     ProductDto toDto(Product product);

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "availability", ignore = true)

    Product toEntity(ProductDto dto);


}
