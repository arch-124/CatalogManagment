package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Entity.Product;
import com.example.Catalog.Managment.Mapper.ProductMapper;
import com.example.Catalog.Managment.Repository.ProductRepository;
import com.example.Catalog.Managment.Service.ProductService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class ProductServiceimpl implements ProductService
{
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;



    @Override
    public ProductDto createProduct(ProductDto dto)
    {
        try {
            if (dto.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero.");
            }

            Product product = new Product();
            product.setName(dto.getName());
            product.setPrice(dto.getPrice());

            product.setDescription("No Description");
            product.setAvailability(true);

            Product saved = productRepository.save(product);
            return productMapper.toDto(saved);
        }
        catch (Exception e)
        {
            log.error("Failed to create Product",e);
            return null;
        }

    }

    @Override
    public ProductDto getProductbyid(int id)
    {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with given id"));
            return productMapper.toDto(product);
        }
            catch(Exception e)
            {
                log.error("Failed to get Product", e);
                return null;
            }

    }

    @Override
    public ProductDto updateProduct(ProductDto dto)
    {
        try {
            Product existing = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            existing.setName(dto.getName());
            existing.setPrice(dto.getPrice());

            Product updated = productRepository.save(existing);
            return productMapper.toDto(updated);
        }
        catch (Exception e)
        {
            log.error("Failed to update Product",e);
            return null;
        }
      
    }

    @Override
    public List<ProductDto> getAllproducts() {
        try
        {
            return productRepository.findByAvailabilityTrue()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }
        catch(Exception e)
        {
            log.error("Failed to get Products",e);
            return null;
        }
    }

    @Override
    public void deleteProduct(int id)
    {
        try
        {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setAvailability(false);
            productRepository.save(product);
        }
        catch(Exception e)
        {
            log.error("Failed to delete Product",e);
        }

    }
}
