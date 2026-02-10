package com.example.Catalog.Managment.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Products")

public class Product
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(length = 100)
    private String name;




    private Boolean availability;

//    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Inventory inventory;

    @PrePersist
    public void setDefaults() {
        this.availability = true;
    }
    @OneToMany(mappedBy = "product")
    private List<Sku> skus = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



}
