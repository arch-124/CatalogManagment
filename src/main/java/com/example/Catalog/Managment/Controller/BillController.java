package com.example.Catalog.Managment.Controller;

import com.example.Catalog.Managment.Dto.Response.BillResponseDto;
import com.example.Catalog.Managment.Entity.Orders;
import com.example.Catalog.Managment.Repository.OrdersRepository;
import com.example.Catalog.Managment.Service.BillService;
import com.example.Catalog.Managment.Service.Pdfservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill-orders")
public class BillController {

    private final OrdersRepository ordersRepository;
    private final BillService billService;
    private final Pdfservice pdfservice;

    @GetMapping("/{orderId}/bill")
    public ResponseEntity<byte[]> downloadBill(@PathVariable Long orderId) {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        BillResponseDto bill = billService.generateBill(order);
        byte[] pdf = pdfservice.generatebillpdf(bill);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=receipt.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }

}
