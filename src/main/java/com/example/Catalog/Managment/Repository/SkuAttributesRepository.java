package com.example.Catalog.Managment.Repository;


import com.example.Catalog.Managment.Entity.SkuAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SkuAttributesRepository extends JpaRepository<SkuAttribute,Long>
{
    List<SkuAttribute> findBySkuId(Long productId);
}
