package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.Request.OrderItemsRequestdto;
import com.example.Catalog.Managment.Dto.Request.OrderRequestdto;

import com.example.Catalog.Managment.Dto.Response.OrderResponsedto;
import com.example.Catalog.Managment.Entity.*;
import com.example.Catalog.Managment.Mapper.OrdersMapper;
import com.example.Catalog.Managment.Repository.CustomerRepository;
import com.example.Catalog.Managment.Repository.InventoryRepository;
import com.example.Catalog.Managment.Repository.OrdersRepository;
import com.example.Catalog.Managment.Repository.ProductRepository;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<OrderResponsedto>> placeOrder(
            OrderRequestdto requestdto) {

        try {
            Customer customer = customerRepository.findById(requestdto.getCustomerId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Customer not found with id: " + requestdto.getCustomerId()
                            ));

            Orders order = new Orders();
            order.setOrderstatus("PLACED");

            List<OrderItem> orderItems = new ArrayList<>();
            double totalAmount = 0;

            for (OrderItemsRequestdto itemDto : requestdto.getItems()) {

                Product product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found with id: " + itemDto.getProductId()
                                ));

                Inventory inventory = inventoryRepository.findByProductId(product.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Inventory not found for product id: " + product.getId()
                                ));

                if (inventory.getQuantity() < itemDto.getQuantity()) {
                    throw new RuntimeException(
                            "Insufficient stock for product id: " + product.getId()
                    );
                }

                // reduce stock
                inventory.setQuantity(
                        inventory.getQuantity() - itemDto.getQuantity()
                );

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(itemDto.getQuantity());
                item.setPriceAtPurchase(product.getPrice());
                item.setTotalPrice(product.getPrice() * itemDto.getQuantity());

                totalAmount += item.getTotalPrice();
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
    public ResponseEntity<ApiResponse<OrderResponsedto>> getById(int id) {

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
