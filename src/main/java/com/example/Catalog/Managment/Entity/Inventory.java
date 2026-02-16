package com.example.Catalog.Managment.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer soldQuantity;


    private Long OrderId;


    @ManyToOne
    @JoinColumn(name = "po_id")
    private PO po;


    @PrePersist
    public void prePersist()
    {
        if(availableQuantity == null) availableQuantity = 0;
        if(reservedQuantity == null) reservedQuantity = 0;
        if(soldQuantity == null) soldQuantity = 0;
    }


    @OneToOne
    @JoinColumn(name = "sku_id", nullable = false)
    private Sku sku;
}
