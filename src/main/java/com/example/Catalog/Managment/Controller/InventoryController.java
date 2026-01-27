package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Entity.Inventory;
import com.example.Catalog.Managment.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController
{
    public final InventoryService inventoryService;

    @PostMapping
    public InventoryDto create(@RequestBody InventoryDto inventoryDto)
    {
        return inventoryService.create(inventoryDto);
    }

    @GetMapping("/product/{productId}")
    public InventoryDto getProductById(@PathVariable int productId )
    {
        return inventoryService.getProductById(productId);
    }

    @PutMapping("/product/{productId}")
    public InventoryDto updatestock(@PathVariable int productId, @RequestParam int stock)
    {
            inventoryService.updatestock(productId, stock);

        return null;
    }





}
