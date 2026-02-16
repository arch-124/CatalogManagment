package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Sku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SkuRepository extends CrudRepository<Sku,Long> {
    List<Sku> findByProductId(Long productId);
    @Query("""
   SELECT s FROM Sku s
   WHERE s.stale = false
   AND s.createdAt < :cutoff
   AND (s.lastSoldAt IS NULL OR s.lastSoldAt < :cutoff)
""")
    List<Sku> findOldUnsoldSkus(LocalDateTime cutoff);
    List<Sku> findAllByStaleTrue();
    List<Sku> findByDiscountActiveTrue();


}
