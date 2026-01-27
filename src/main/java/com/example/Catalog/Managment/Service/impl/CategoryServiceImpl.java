package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Entity.Category;
import com.example.Catalog.Managment.Mapper.CategoryMappper;
import com.example.Catalog.Managment.Repository.CategoryRepository;
import com.example.Catalog.Managment.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                throw new RuntimeException("failed to save category");
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
        catch(RuntimeException e )
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Category not found",e);
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
            throw new RuntimeException("Error fetching categories",e);
        }
    }
}
