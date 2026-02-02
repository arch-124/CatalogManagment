package com.example.Catalog.Managment.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.ap.internal.model.GeneratedType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ordernumber;
    private LocalDate orderdate;
    private String orderstatus;
    private Double totalamount;

    @PrePersist
    void  prePersist()
    {
        this.orderdate = LocalDate.now();
    }
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;
}
