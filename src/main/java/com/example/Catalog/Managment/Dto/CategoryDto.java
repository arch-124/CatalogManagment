package com.example.Catalog.Managment.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto
{
    private Integer id;
    private String name;
    private String description;
}
