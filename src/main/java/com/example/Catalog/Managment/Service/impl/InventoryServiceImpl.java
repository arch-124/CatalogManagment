package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.InventoryDto;
import com.example.Catalog.Managment.Entity.Inventory;
import com.example.Catalog.Managment.Entity.Product;
import com.example.Catalog.Managment.Mapper.InventoryMapper;
import com.example.Catalog.Managment.Repository.InventoryRepository;
import com.example.Catalog.Managment.Repository.ProductRepository;
import com.example.Catalog.Managment.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService
{
   public final InventoryRepository inventoryRepository;
   public final InventoryMapper inventoryMapper;
   public final ProductRepository productRepository;

    @Override
    public InventoryDto create(InventoryDto dto)
    {
        try
        {
            Product product = productRepository
                    .findById(dto.getProductId())
                    .orElseThrow(()-> new RuntimeException("product not found with given id"));
            Inventory inventory = inventoryMapper.toEntity(dto);
            inventory.setProduct(product);
            Inventory inventorySaved = inventoryRepository.save(inventory);
            return inventoryMapper.toDto(inventorySaved);
        } catch (Exception e) {
            log.error("failed to return category", e);
            return null;
        }



    }

    @Override
    public InventoryDto getProductById(int productId)
    {
        try
        {
            Inventory inventory = inventoryRepository.findById(productId).orElseThrow(() -> new RuntimeException("Inventory not found for given product id"));
            return inventoryMapper.toDto(inventory);
        }
        catch (Exception e)
        {
           log.error("failed to fetch Inventory", e);
           return null;


        }

    }

    @Override
    public void updatestock(int productId, int stock)
    {
        try
        {
            Inventory inventory = inventoryRepository.findById(productId).orElseThrow(()->new RuntimeException("inventory not found for given product id "));
            inventory.setQuantity(stock);
            inventoryRepository.save(inventory);
        }

    catch(Exception e)
        {
            log.error("failed to update inventory stock", e);
        }
    }
}
