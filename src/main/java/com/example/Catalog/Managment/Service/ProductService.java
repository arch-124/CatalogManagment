package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Repository.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService
{
    ProductDto createProduct(ProductDto dto);
    ProductDto getProductbyid(int id);
    ProductDto updateProduct(ProductDto dto);
    List<ProductDto> getAllproducts();
    void deleteProduct(int id);


}
