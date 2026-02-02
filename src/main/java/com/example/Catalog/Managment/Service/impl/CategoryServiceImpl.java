package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Entity.Category;
import com.example.Catalog.Managment.Mapper.CategoryMappper;
import com.example.Catalog.Managment.Repository.CategoryRepository;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;
    private final CategoryMappper categoryMappper;

    @Override
    public ResponseEntity<ApiResponse<CategoryDto>> create(CategoryDto categoryDto)
    {
        try
        {
            Category category = categoryMappper.toEntity(categoryDto);
            Category saved = categoryRepository.save(category);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            HttpStatus.CREATED.value(),
                            "Category created successfully",
                            categoryMappper.toDto(saved)
                    ));
        }
        catch (Exception e)
        {
            log.error("Failed to save category", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create category",
                            null
                    ));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<CategoryDto>> getById(int id)
    {
        try
        {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            HttpStatus.OK.value(),
                            "Category fetched successfully",
                            categoryMappper.toDto(category)
                    ));
        }
        catch (Exception e)
        {
            log.warn("Category not found", e);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.NOT_FOUND.value(),
                            "Category not found",
                            null
                    ));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAll()
    {
        try
        {
            List<CategoryDto> categories = categoryRepository.findAll()
                    .stream()
                    .map(categoryMappper::toDto)
                    .toList();

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            HttpStatus.OK.value(),
                            "Categories fetched successfully",
                            categories
                    ));
        }
        catch (Exception e)
        {
            log.error("Error fetching categories", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to fetch categories",
                            null
                    ));
        }
    }
}
