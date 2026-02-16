package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Product;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long>
{
    List<Product> findByAvailabilityTrue();
    List<Product> findByName(String name);
    @Query("""
   SELECT p FROM Product p
   JOIN FETCH p.category
   WHERE p.category.id = :categoryId
   AND p.availability = true
""")
    List<Product> findByCategory_IdAndAvailabilityTrue(Long categoryId);



}
