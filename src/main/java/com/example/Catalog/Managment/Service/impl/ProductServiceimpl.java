package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Entity.Product;
import com.example.Catalog.Managment.Mapper.ProductMapper;
import com.example.Catalog.Managment.Repository.ProductRepository;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.ProductService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(ProductDto dto)
    {
        try {
            if (dto.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero.");
            }

            Product product = productMapper.toEntity(dto);
            Product saved = productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ApiResponse(
                                    true,
                                    HttpStatus.CREATED.value(),
                                    "Product created successfully",
                                    productMapper.toDto(saved)

                            ));
        }
        catch (Exception e)
        {
            log.error("Failed to create Product",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create Product",
                            null
                    ));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<ProductDto>> getProductbyid(int id)
    {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with given id"));
            return ResponseEntity.ok(
                    new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Fetched product by id successfully",
                    productMapper.toDto(product)

            ));
        }
            catch(Exception e)
            {
                log.error("Failed to get Product", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(
                                false,
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Failed to fetch product by id",
                                null

                        ));
            }

    }

    @Override
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(ProductDto dto)
    {
        try {
            Product existing = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            existing.setName(dto.getName());
            existing.setPrice(dto.getPrice());

            Product updated = productRepository.save(existing);
            return ResponseEntity.ok(
                    new  ApiResponse<>(
                            true,
                            HttpStatus.OK.value(),
                            "Updated product successfully",
                            productMapper.toDto(updated)
                    )
            );
        }
        catch (Exception e)
        {
            log.error("Failed to update Product",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to update product",
                            null
                            )
                    );
        }
      
    }

    @Override
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllproducts() {
        try
        {
            List<ProductDto> products = productRepository.findByAvailabilityTrue()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
            return ResponseEntity.ok(new  ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "fetched all products successfully",
                    products

            ));
        }
        catch(Exception e)
        {
            log.error("Failed to get Products",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to get all products",
                            null
                    ));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteProduct(int id)
    {
        try
        {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setAvailability(false);
             return ResponseEntity.ok
                     (new ApiResponse<>(
                     true,
                     HttpStatus.OK.value(),
                     "Deleted product successfully",
                     "Deleted product with id: "+ id

             ));
        }
        catch(Exception e)
        {
            log.error("Failed to delete Product",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to delete product",
                            null
                    ));
        }

    }
}
