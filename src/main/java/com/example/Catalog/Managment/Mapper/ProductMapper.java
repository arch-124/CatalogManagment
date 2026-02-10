package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Dto.ProductcreateDto;
import com.example.Catalog.Managment.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper
{
    @Mapping(source = "category.id", target = "categoryId")
     ProductDto toDto(Product product);


    @Mapping(source = "id", target = "category.id")
    Product toEntity(ProductDto dto);


}
