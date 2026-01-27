package com.example.Catalog.Managment.Mapper;

import com.example.Catalog.Managment.Dto.CategoryDto;
import com.example.Catalog.Managment.Entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMappper
{
    CategoryDto toDto(Category category);

    @Mapping(target = "products",ignore = true)
    Category toEntity(CategoryDto categoryDto);
}
