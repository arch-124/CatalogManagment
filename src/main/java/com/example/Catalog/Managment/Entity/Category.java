package com.example.Catalog.Managment.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean active;

    @PrePersist
    void oncreate()
    {
        this.active = true;
    }
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}

