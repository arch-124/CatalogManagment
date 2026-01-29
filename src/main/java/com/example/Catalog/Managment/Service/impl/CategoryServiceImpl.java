package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Entity.Category;
import com.example.Catalog.Managment.Mapper.CategoryMappper;
import com.example.Catalog.Managment.Repository.CategoryRepository;
import com.example.Catalog.Managment.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;
    private  final CategoryMappper categoryMappper;


    @Override
    public CategoryDto create(CategoryDto categoryDto)
    {
        try
        {
            Category category = categoryMappper.toEntity(categoryDto);
            Category saved = categoryRepository.save(category);
            return categoryMappper.toDto(saved);
        }
        catch (Exception e)
        {
                log.error("failed to save category",e);
            return null;
        }

    }

    @Override
    public CategoryDto getById(int id)
    {
        try
        {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
            return categoryMappper.toDto(category);
        }
        catch(Exception e)
        {
            log.warn("Category not found",e);
            return null;
        }

    }

    @Override
    public List<CategoryDto> getAll()
    {
        try
        {
            return categoryRepository.findAll()
                    .stream()
                    .map(categoryMappper::toDto)
                    .toList();
        }
        catch(Exception e)
        {
          log.error("Error fetching categories",e);
            return null;
        }
    }
}
