package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Entity.Product;
import com.example.Catalog.Managment.Mapper.ProductMapper;
import com.example.Catalog.Managment.Repository.ProductRepository;
import com.example.Catalog.Managment.Service.ProductService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new RuntimeException("Failed to create Product",e);
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
                throw new RuntimeException("Failed to get Product", e);
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
            throw new RuntimeException("Failed to update Product",e);
        }
      
    }

    @Override
    public List<ProductDto> getAllproducts() {
        try
        {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to get Products",e);
        }
    }

    @Override
    public void deleteProduct(int id)
    {
        try
        {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            productRepository.delete(product);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to delete Product",e);
        }

    }
}
