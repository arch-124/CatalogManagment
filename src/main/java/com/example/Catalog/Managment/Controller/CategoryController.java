package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController
{
   private final CategoryService categoryService;

   @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> create(@Valid @RequestBody CategoryDto categoryDto)
   {
      return categoryService.create(categoryDto);

   }

   @GetMapping("/{id}")
   public ResponseEntity<ApiResponse<CategoryDto>> getById(@PathVariable Long id)
   {
       return categoryService.getById(id);

   }

   @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAll()
   {
       return categoryService.getAll();

   }

}
