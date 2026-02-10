package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.BillitemDto;
import com.example.Catalog.Managment.Dto.Response.BillResponseDto;
import com.example.Catalog.Managment.Entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BillService
{

    public BillResponseDto generateBill(Orders orders)
    {
        List<BillitemDto> items = orders.getOrderItems().stream()
                .map(item -> BillitemDto.builder()
                        .productName(item.getSku().getProduct().getName())
                        .skuCode(item.getSku().getSkucode())
                        .quantity(item.getQuantity())
                        .price(item.getPriceAtPurchase())
                        .total(item.getTotalPrice())
                        .build()
                ).toList();
        BigDecimal subTotal = orders.getTotalamount();
        BigDecimal tax = subTotal.multiply(BigDecimal.valueOf(0.18));
        BigDecimal grandTotal = subTotal.add(tax);

        return BillResponseDto.builder()
                .orderId(orders.getId())
                .orderStatus(orders.getOrderstatus())
                .orderDate(orders.getOrderdate())
                .customerName(orders.getCustomer().getName())
                .customerEmail(orders.getCustomer().getEmail())
                .items(items)
                .subtotal(subTotal)
                .tax(tax)
                .grandTotal(grandTotal)
                .build();
    }
}
