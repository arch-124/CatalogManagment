package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController
{
    private final ProductService service;
    @PostMapping
        public ProductDto create(@RequestBody ProductDto dto)
        {
            return service.createProduct(dto);
        }
    @GetMapping("/{id}")
            public ProductDto getProductbyid(@PathVariable int id)
            {
                return service.getProductbyid(id);
            }
    @GetMapping
        public List<ProductDto> getAll()
        {
            return service.getAllproducts();
        }
    @PutMapping("/{id}")
        public ProductDto updateProduct(@PathVariable int id, @RequestBody ProductDto dto)
        {
            dto.setId(id);
            return service.updateProduct(dto);
        }
    @DeleteMapping("/{id}")
        public void deleteProduct(@PathVariable int id)
        {
            service.deleteProduct(id);
        }




}
