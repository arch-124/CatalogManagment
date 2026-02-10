package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findBySkuId(Long skuId);
}