package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Entity.Inventory;
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
    public ResponseEntity <InventoryDto> create(@RequestBody InventoryDto inventoryDto)
    {
        InventoryDto created = inventoryService.create(inventoryDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDto> getProductById(@PathVariable int productId )
    {
        InventoryDto products =  inventoryService.getProductById(productId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity <Void>  updatestock(@PathVariable int productId, @RequestParam int stock)
    {
            inventoryService.updatestock(productId, stock);
            return ResponseEntity.noContent().build();



    }





}
