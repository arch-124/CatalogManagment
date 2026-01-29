package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.ProductDto;
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
        public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto)
        {
           ProductDto created =  service.createProduct(dto);
           return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }


    @GetMapping("/{id}")
            public ResponseEntity<ProductDto> getProductbyid(@PathVariable int id)
            {
                ProductDto product = service.getProductbyid(id);
                return ResponseEntity.ok(product);
            }
    @GetMapping
        public ResponseEntity<List<ProductDto>> getAll()
        {
           List<ProductDto> products = service.getAllproducts();
           return ResponseEntity.ok(products);

        }
    @PutMapping("/{id}")
        public ResponseEntity<ProductDto> updateProduct(@PathVariable int id, @RequestBody ProductDto dto)
        {
            dto.setId(id);
            ProductDto updated =  service.updateProduct(dto);
            return ResponseEntity.ok(updated);
        }
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable int id)
        {
            service.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }




}
