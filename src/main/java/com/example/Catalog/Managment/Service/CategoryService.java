package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService
{
    ResponseEntity<ApiResponse<CategoryDto>> create(CategoryDto categoryDto);
    ResponseEntity<ApiResponse<CategoryDto>> getById(int id);
    ResponseEntity<ApiResponse<List<CategoryDto>>> getAll();

}
