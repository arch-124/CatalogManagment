package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}