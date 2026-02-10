package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper
{
    @Mapping(source = "sku.id",target = "skuId")
    InventoryDto toDto(Inventory inventory);

    @Mapping(target = "sku", ignore = true)
    Inventory toEntity(InventoryDto inventoryDto);
}
