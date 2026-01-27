package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController
{
   private final CategoryService categoryService;

   @PostMapping
    public CategoryDto create(@RequestBody CategoryDto categoryDto)
   {
       return categoryService.create(categoryDto);
   }

   @GetMapping("/{id}")
   public CategoryDto getById(@PathVariable int id)
   {
       return categoryService.getById(id);
   }

   @GetMapping
    public List<CategoryDto> getAll()
   {
       return categoryService.getAll();
   }

}
