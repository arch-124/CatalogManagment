package com.example.Catalog.Managment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Column(nullable = false,updatable = false)
    private LocalDate createdAt;

    @PrePersist
    void  onCreate()
    {
        this.createdAt = LocalDate.now();
    }
    @OneToMany(mappedBy = "customer")
    private List<Orders> orders;


}
