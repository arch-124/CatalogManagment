package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer>
{
}
