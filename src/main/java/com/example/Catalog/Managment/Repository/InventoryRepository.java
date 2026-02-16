package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuId(Long skuId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Inventory i where i.sku.id = :skuId")
    Optional<Inventory> findBySkuIdForUpdate(@Param("skuId") Long skuId);
}