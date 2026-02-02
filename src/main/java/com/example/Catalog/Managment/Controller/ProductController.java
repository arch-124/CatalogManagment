package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController
{
    private final ProductService service;
    @PostMapping
        public ResponseEntity<ApiResponse<ProductDto>> create(@RequestBody ProductDto dto)
        {
           return service.createProduct(dto);

        }


    @GetMapping("/{id}")
            public ResponseEntity<ApiResponse<ProductDto>> getProductbyid(@PathVariable int id)
            {
                return service.getProductbyid(id);

            }
    @GetMapping
        public ResponseEntity<ApiResponse<List<ProductDto>>> getAll()
        {
           return service.getAllproducts();

        }
    @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable int id, @RequestBody ProductDto dto)
        {

            return service.updateProduct(dto);

        }
    @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable int id)
        {
            return service.deleteProduct(id);

        }




}
