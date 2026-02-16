package com.example.Catalog.Managment.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private BigDecimal originalPrice;   // restore after discount

    private Boolean active;

    private Boolean stale;              // set by scheduler

    @Column(nullable = false, unique = true)
    private String skucode;

    private LocalDateTime createdAt;
    private LocalDateTime lastSoldAt;

    // DISCOUNT FIELDS
    private BigDecimal discountPercent;
    private Boolean discountActive;

    private LocalDateTime discountStart;
    private LocalDateTime discountEnd;



    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL)
    private List<SkuAttribute> attributes;


    @PrePersist
    public void prePersist()
    {
        if(createdAt == null)
            createdAt = LocalDateTime.now();

        if(originalPrice == null)
            originalPrice = price;

        if(active == null)
            active = true;

        if(stale == null)
            stale = false;

        if(discountActive == null)
            discountActive = false;
    }


}
