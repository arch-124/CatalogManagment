package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.InventoryDto;

public interface InventoryService
{
    InventoryDto create(InventoryDto dto);
    InventoryDto getProductById(int productId);
    void updatestock(int productId, int stock);

}
