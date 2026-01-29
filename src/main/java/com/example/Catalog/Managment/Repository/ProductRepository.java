package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer>
{
    List<Product> findByAvailabilityTrue();
}
