package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Entity.Category;

import java.util.List;

public interface CategoryService
{
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto getById(int id);
    List<CategoryDto> getAll();

}
