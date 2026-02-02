package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface InventoryService
{
    ResponseEntity<ApiResponse<InventoryDto>> create(InventoryDto dto);
    ResponseEntity<ApiResponse<InventoryDto>> getProductById(int productId);
    ResponseEntity<ApiResponse<String>> updatestock(int productId, int stock);

}
