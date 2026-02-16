package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Entity.Inventory;
import com.example.Catalog.Managment.Entity.Product;
import com.example.Catalog.Managment.Entity.Sku;
import com.example.Catalog.Managment.Repository.InventoryRepository;
import com.example.Catalog.Managment.Repository.SkuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class Schedulerservice {

    private final SkuRepository skuRepository;
    private final InventoryRepository inventoryRepository;


    @Scheduled(fixedRate = 300000)
    @Transactional
    public void markStaleSku() {

        log.info("running stale sku scheduler");

        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);

        List<Sku> oldSkus = skuRepository.findOldUnsoldSkus(cutoff);

        for (Sku sku : oldSkus) {

            //skip already stale
            if (Boolean.TRUE.equals(sku.getStale())) continue;

            // checking inventory row in sku
            Inventory inv = inventoryRepository.findBySkuId(sku.getId()).orElse(null);
            if (inv == null) continue;

            Integer available = inv.getAvailableQuantity();
            if (available == null || available <= 0) continue;

            // skip if supplier already running discount
            if (Boolean.TRUE.equals(sku.getDiscountActive())) continue;

            // mark stale once
            sku.setStale(true);
            sku.setActive(false);
        }
    }

    @Scheduled(fixedRate = 1200000)
    @Transactional
    public void autoIncrementDiscount()
    {
        List<Sku> list = skuRepository.findAllByStaleTrue();
        for (Sku sku : list)
        {
            if(!Boolean.TRUE.equals(sku.getDiscountActive()))
                continue;

            BigDecimal currentdiscount = sku.getDiscountPercent();
            BigDecimal newpercent = currentdiscount.add(BigDecimal.valueOf(5));

            if(newpercent.compareTo(BigDecimal.valueOf(70)) > 0)
                continue;

            sku.setDiscountPercent(newpercent);

            BigDecimal newprice = sku.getOriginalPrice().subtract(
                    sku.getOriginalPrice()
                            .multiply(newpercent)
                            .divide(BigDecimal.valueOf(100))
            );

            sku.setPrice(newprice);


        }

    }

    @Transactional
    public void expiredDiscounts()
    {
        LocalDateTime now = LocalDateTime.now();
        List<Sku> list = skuRepository.findByDiscountActiveTrue();

        for(Sku sku : list)
        {
            if(sku.getDiscountEnd() != null && now.isAfter(sku.getDiscountEnd()))
            {
                sku.setDiscountActive(false);
                sku.setPrice(sku.getOriginalPrice());
            }
        }

    }
}
