package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface InventoryService
{
    ResponseEntity<ApiResponse<InventoryDto>> create(InventoryDto dto);
    ResponseEntity<ApiResponse<InventoryDto>> getProductById(Long productId);
    ResponseEntity<ApiResponse<String>> updatestock(Long productId, Long stock);

}
