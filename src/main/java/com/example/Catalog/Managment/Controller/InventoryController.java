package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Entity.Inventory;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController
{
    public final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity <ApiResponse<InventoryDto>> create(@RequestBody InventoryDto inventoryDto)
    {
        return inventoryService.create(inventoryDto);

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<InventoryDto>> getProductById(@PathVariable int productId )
    {
        return inventoryService.getProductById(productId);

    }

    @PutMapping("/product/{productId}")
    public ResponseEntity <ApiResponse<String>>  updatestock(@PathVariable int productId, @RequestParam int stock)
    {
           return inventoryService.updatestock(productId, stock);




    }





}
