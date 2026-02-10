package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.SkuDto;
import com.example.Catalog.Managment.Entity.Sku;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SkuMapper {

    @Mapping(source = "price", target = "price")
    @Mapping(target = "attributes", ignore = true)
    Sku toEntity(SkuDto dto);

    @Mapping(source = "price", target = "price")
    @Mapping(target = "attributes", ignore = true)
    SkuDto toDto(Sku sku);
}
