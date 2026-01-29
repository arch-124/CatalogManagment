package com.example.Catalog.Managment.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto
{
    private Integer id;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @Size(min = 3 , max = 200)
    private String description;
}
