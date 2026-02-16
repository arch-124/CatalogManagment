package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.SupplierDiscountDto;
import com.example.Catalog.Managment.Service.impl.SupplierDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("supplier")
@RequiredArgsConstructor
public class SupplierController
{
    private final SupplierDiscountService supplierDiscountService;

    @PostMapping("/sku/{skuId}/discount")
    public ResponseEntity<String> setDiscount(@PathVariable Long skuId, @RequestBody SupplierDiscountDto supplierdiscountdto)
    {
        supplierDiscountService.applySupplierDiscount(
                skuId,
                supplierdiscountdto.getDiscountPercent(),
                supplierdiscountdto.getDays()
        );

        return ResponseEntity.ok("discount applied");
    }
}
