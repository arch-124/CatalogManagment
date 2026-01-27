package com.example.Catalog.Managment.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inventory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int quantity;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
