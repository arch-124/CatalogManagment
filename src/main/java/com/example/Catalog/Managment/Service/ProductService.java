package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Dto.ProductcreateDto;
import com.example.Catalog.Managment.Entity.Product;
import com.example.Catalog.Managment.Response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService
{
    ResponseEntity<ApiResponse<ProductDto>> createProduct(ProductcreateDto dto);
    ResponseEntity<ApiResponse<ProductDto>> getProductbyid(int id);
    ResponseEntity<ApiResponse<ProductDto>> updateProduct(ProductDto dto);
    ResponseEntity<ApiResponse<List<ProductDto>>> getAllproducts();
    ResponseEntity<ApiResponse<String>> deleteProduct(int id);


    ResponseEntity<ApiResponse<ProductDto>> updateCategory(int id,ProductDto dto);
}
