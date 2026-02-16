package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Entity.Inventory;
import com.example.Catalog.Managment.Entity.Sku;
import com.example.Catalog.Managment.Mapper.InventoryMapper;
import com.example.Catalog.Managment.Repository.InventoryRepository;
import com.example.Catalog.Managment.Repository.SkuRepository;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService
{
   public final InventoryRepository inventoryRepository;
   public final InventoryMapper inventoryMapper;
   public final SkuRepository skuRepository;


    @Override
    public ResponseEntity<ApiResponse<InventoryDto>> create(InventoryDto dto)
    {
        try
        {
            Sku sku = skuRepository.findById(dto.getSkuId())
                    .orElseThrow(() -> new RuntimeException("SKU not found"));

            Inventory inventory = new Inventory();
            inventory.setSku(sku);
            inventory.setQuantity(Long.valueOf(dto.getQuantity()));

            Inventory inventorySaved = inventoryRepository.save(inventory);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                    true,
                    HttpStatus.CREATED.value(),
                    "Inventory created successfully",
                    inventoryMapper.toDto(inventorySaved)

            ));
        } catch (Exception e) {
            log.error("failed to return category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create Inventory",
                            null
                    ));
        }


    }

    @Override
    public ResponseEntity<ApiResponse<InventoryDto>> getProductById(Long productId)
    {
        try
        {
            Inventory inventory = inventoryRepository.findById(productId).orElseThrow(() -> new RuntimeException("Inventory not found for given product id"));
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Successfully fetched product by id from inventory",
                    inventoryMapper.toDto(inventory)
            ));
        }
        catch (Exception e)
        {
           log.error("failed to fetch Inventory", e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse<>(
                           false,
                           HttpStatus.INTERNAL_SERVER_ERROR.value(),
                           "Failed to fetech product by id from Inventory",
                           null

                   )
           );


        }

    }

    @Override
    public ResponseEntity<ApiResponse<String>> updatestock(Long productId, Long stock)
    {
        try
        {
            Inventory inventory = inventoryRepository.findById(productId).orElseThrow(()->new RuntimeException("inventory not found for given product id "));
            inventory.setQuantity(stock);
             inventoryRepository.save(inventory);
             return ResponseEntity.ok()
                     .body(new ApiResponse<>(
                             true,
                             HttpStatus.OK.value(),
                             "Inventory stock updated successfully",
                             "productid: "+ productId + "stock: " + stock
                     ));
        }

    catch(Exception e)
        {
            log.error("failed to update inventory stock", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        false,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Failed to update inventory stock",
                        null));
    }
}
