package com.example.Catalog.Managment.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;


import javax.management.Attribute;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sku
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    private Boolean active;
    @Column(nullable = false,unique = true)
    private String skucode;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL)
    private List<SkuAttribute> attributes;

}
