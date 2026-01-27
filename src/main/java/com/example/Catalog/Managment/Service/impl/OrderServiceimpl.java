package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.OrderItemsDto;
import com.example.Catalog.Managment.Dto.OrdersDto;
import com.example.Catalog.Managment.Entity.Inventory;
import com.example.Catalog.Managment.Entity.OrderItem;
import com.example.Catalog.Managment.Entity.Orders;
import com.example.Catalog.Managment.Entity.Product;
import com.example.Catalog.Managment.Mapper.OrdersMapper;
import com.example.Catalog.Managment.Repository.InventoryRepository;
import com.example.Catalog.Managment.Repository.OrdersRepository;
import com.example.Catalog.Managment.Repository.ProductRepository;
import com.example.Catalog.Managment.Service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceimpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final OrdersMapper ordersMapper;

    @Override
    @Transactional
    public OrdersDto placeOrder(OrdersDto ordersDto) {
        try {
            Orders order = new Orders();
//            order.setOrdernumber(ordersDto.getOrdernumber());
//            order.setOrderdate(ordersDto.getOrderdate());
            order.setOrderstatus("PLACED");

            List<OrderItem> orderItems = new ArrayList<>();
            double totalAmount = 0;

            for (OrderItemsDto itemDto : ordersDto.getOrderItems()) {

                Product product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException("Product not found with id: " + itemDto.getProductId()));

                Inventory inventory =  inventoryRepository.findByProductId(product.getId())
                        .orElseThrow(()-> new RuntimeException("Inventory not found with id: " + product.getId()));


                if (inventory.getQuantity() < itemDto.getQuantity()) {
                    throw new RuntimeException(
                            "Insufficient stock for product id: " + product.getId());
                }

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(itemDto.getQuantity());
                item.setPriceAtPurchase(product.getPrice());
                item.setTotalPrice(product.getPrice() * itemDto.getQuantity());

                inventory.setQuantity(
                        inventory.getQuantity() - itemDto.getQuantity()
                );
                inventoryRepository.save(inventory);

                totalAmount += item.getTotalPrice();
                orderItems.add(item);
            }

            order.setTotalamount(totalAmount);
            order.setOrderItems(orderItems);

            Orders savedOrder = ordersRepository.save(order);
            return ordersMapper.toDto(savedOrder);

        } catch (Exception e) {
            throw new RuntimeException("Failed to place order", e);
        }
    }

    @Override
    public OrdersDto getById(int id) {
        try {
            Orders order = ordersRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Order not found with id: " + id));

            return ordersMapper.toDto(order);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch order with id: " + id, e);
        }
    }
}
