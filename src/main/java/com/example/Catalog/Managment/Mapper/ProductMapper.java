package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Dto.ProductcreateDto;
import com.example.Catalog.Managment.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {SkuMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "availability", constant = "true")
    Product toEntity(ProductcreateDto dto);

    ProductDto toDto(Product product);
}

