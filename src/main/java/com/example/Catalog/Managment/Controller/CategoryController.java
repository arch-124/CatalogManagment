package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.ScalarToken;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController
{
   private final CategoryService categoryService;

   @PostMapping
    public ResponseEntity <CategoryDto> create(@RequestBody CategoryDto categoryDto)
   {
       CategoryDto created = categoryService.create(categoryDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(created);
   }

   @GetMapping("/{id}")
   public ResponseEntity <CategoryDto> getById(@PathVariable int id)
   {
       CategoryDto catgories =  categoryService.getById(id);
       return ResponseEntity.ok(catgories);
   }

   @GetMapping
    public ResponseEntity <List<CategoryDto>> getAll()
   {
       List <CategoryDto> catgories =  categoryService.getAll();
       return ResponseEntity.ok(catgories);
   }

}
