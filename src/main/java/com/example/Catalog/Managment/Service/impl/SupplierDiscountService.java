package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Entity.Sku;
import com.example.Catalog.Managment.Repository.SkuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class SupplierDiscountService
{
    private final SkuRepository skuRepository;

    @Transactional
   public void  applySupplierDiscount(Long skuId, BigDecimal percent, int days)
    {
        Sku sku = skuRepository.findById(skuId)
                .orElseThrow(() -> new RuntimeException("sku not found"));

        if(!Boolean.TRUE.equals(sku.getStale()))
            throw new RuntimeException("sku must be stale to get discount");
        if(Boolean.TRUE.equals(sku.getDiscountActive()))
            throw new RuntimeException("discount is already active");

        sku.setDiscountPercent(percent);
        sku.setDiscountStart(LocalDateTime.now());
        sku.setDiscountEnd(LocalDateTime.now().plusDays(days));
        sku.setDiscountActive(true);

        BigDecimal base = sku.getOriginalPrice();

        if(base == null)
        {
            base = sku.getPrice();
            sku.setOriginalPrice(base);
        }

        BigDecimal newprice = base.subtract(
                base.multiply(percent).divide(BigDecimal.valueOf(100))
        );

        sku.setPrice(newprice);
    }

}
