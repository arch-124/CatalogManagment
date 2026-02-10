package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.Request.OrderItemsRequestdto;
import com.example.Catalog.Managment.Dto.Request.OrderRequestdto;

import com.example.Catalog.Managment.Dto.Response.BillResponseDto;
import com.example.Catalog.Managment.Dto.Response.OrderResponsedto;
import com.example.Catalog.Managment.Entity.*;
import com.example.Catalog.Managment.Mapper.OrdersMapper;
import com.example.Catalog.Managment.Repository.*;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.BillService;
import com.example.Catalog.Managment.Service.OrderService;
import com.example.Catalog.Managment.Service.Pdfservice;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceimpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final OrdersMapper ordersMapper;
    private final CustomerRepository customerRepository;
    private final SkuRepository skuRepository;
    private final BillService billService;
    private final Pdfservice pdfService;


    @Override
    @Transactional
    public ResponseEntity<ApiResponse<OrderResponsedto>> placeOrder(
            OrderRequestdto requestdto) {

        try
        {
            Customer customer = customerRepository.findById(requestdto.getCustomerId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Customer not found with id: " + requestdto.getCustomerId()
                            ));

            Orders order = new Orders();
            order.setOrderstatus("PLACED");
            order.setCustomer(customer);


            List<OrderItem> orderItems = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (OrderItemsRequestdto itemDto : requestdto.getItems()) {

                Sku sku = skuRepository.findById(itemDto.getSkuId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sku not found with id: " + itemDto.getSkuId()
                                ));
                if (!Boolean.TRUE.equals(sku.getActive())) {
                    throw new RuntimeException(
                            "Product unavailable. SKU is inactive: " + sku.getSkucode());
                }

                Inventory inventory = inventoryRepository.findBySkuId(sku.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Inventory not found for Sku id: " + sku.getId()
                                ));

                if (inventory.getQuantity() < itemDto.getQuantity()) {
                    throw new RuntimeException(
                            "Insufficient stock for Sku id: " + sku.getId()
                    );
                }

                // reduce stock
                inventory.setQuantity(
                        inventory.getQuantity() - itemDto.getQuantity()
                );
                    if (inventory.getQuantity() == 0) {
                        sku.setActive(false); //deactivates sku
                    }

                Product product = sku.getProduct();

                boolean anyActiveSku = product.getSkus()
                        .stream()
                        .anyMatch(s -> Boolean.TRUE.equals(s.getActive()));

                if (!anyActiveSku) {
                    product.setAvailability(false);
                }


                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setSku(sku);
                item.setQuantity(itemDto.getQuantity());

                BigDecimal price = sku.getPrice();
                BigDecimal quantity = BigDecimal.valueOf(itemDto.getQuantity());

                item.setPriceAtPurchase(price);
                BigDecimal itemTotal = price.multiply(quantity);
                item.setTotalPrice(itemTotal);

                totalAmount = totalAmount.add(itemTotal);
                orderItems.add(item);
            }

            order.setTotalamount(totalAmount);
            order.setOrderItems(orderItems);

            Orders savedOrder = ordersRepository.save(order);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            HttpStatus.CREATED.value(),
                            "Order placed successfully",
                            ordersMapper.toDto(savedOrder)
                    ));

        } catch (Exception e) {
            log.error("Failed to place order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage(),
                            null
                    ));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OrderResponsedto>> getById(Long id) {

        try {
            Orders order = ordersRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Order not found with id: " + id));

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            HttpStatus.OK.value(),
                            "Fetched order successfully",
                            ordersMapper.toDto(order)
                    ));

        } catch (Exception e) {
            log.error("Failed to fetch order with id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage(),
                            null
                    ));
        }
    }
}
