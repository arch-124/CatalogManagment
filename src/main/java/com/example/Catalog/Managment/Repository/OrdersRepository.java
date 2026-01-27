package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}