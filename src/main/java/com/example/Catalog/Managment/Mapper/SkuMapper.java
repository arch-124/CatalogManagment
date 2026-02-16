package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.SkuCreateDto;
import com.example.Catalog.Managment.Dto.SkuDto;
import com.example.Catalog.Managment.Entity.Sku;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SkuMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "skucode", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "attributes", ignore = true)
    Sku toEntity(SkuCreateDto dto);

}
