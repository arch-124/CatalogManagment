package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.Request.OrderItemsRequestdto;
import com.example.Catalog.Managment.Dto.Request.OrderRequestdto;

import com.example.Catalog.Managment.Dto.Response.BillResponseDto;
import com.example.Catalog.Managment.Dto.Response.OrderResponsedto;
import com.example.Catalog.Managment.Entity.*;
import com.example.Catalog.Managment.Enum.OrderStatus;
import com.example.Catalog.Managment.Mapper.OrdersMapper;
import com.example.Catalog.Managment.Repository.*;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.BillService;
import com.example.Catalog.Managment.Service.OrderService;
import com.example.Catalog.Managment.Service.Pdfservice;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
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
            order.setOrderstatus(OrderStatus.RESERVED);
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

                int qty = itemDto.getQuantity();

                if(inventory.getAvailableQuantity() < qty)
                {
                    throw new RuntimeException("Insufficient stock");

                }


                inventory.setAvailableQuantity(inventory.getAvailableQuantity()-qty);
                inventory.setReservedQuantity(inventory.getReservedQuantity()+qty);

                    if (inventory.getAvailableQuantity() == 0) {
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

    @Transactional
    public ResponseEntity<ApiResponse<OrderResponsedto>> confirmOrder(Long orderId)
    {
        try
        {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            if(order.getOrderstatus() != OrderStatus.RESERVED)
            {
                throw new RuntimeException("Only reserved orders can be confrimed");
            }

            for(OrderItem items : order.getOrderItems())
            {
                Inventory inventory = inventoryRepository.findBySkuIdForUpdate(items.getSku().getId())
                        .orElseThrow();
                int qty = items.getQuantity();

                inventory .setReservedQuantity(inventory.getReservedQuantity() - qty);
                inventory.setSoldQuantity(inventory.getSoldQuantity() + qty);

            }

            order.setOrderstatus(OrderStatus.CONFIRMED);

            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "order confirmed successfully",
                    ordersMapper.toDto(order)
            ));

        } catch (Exception e)
        {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse<>(
                           false,
                           HttpStatus.INTERNAL_SERVER_ERROR.value(),
                           e.getMessage(),
                           null

                   ));
        }


    }

    @Transactional
    public ResponseEntity<ApiResponse<OrderResponsedto>> cancelOrder(Long orderId)
    {
        try
        {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            if(order.getOrderstatus() != OrderStatus.RESERVED)
            {
                throw new RuntimeException("Only reserved orders can be cancelled");
            }

            for(OrderItem item : order.getOrderItems())
            {
                Inventory inventory = inventoryRepository.findBySkuIdForUpdate(item.getSku().getId())
                        .orElseThrow();
                int qty = item.getQuantity();

                inventory .setReservedQuantity(inventory.getReservedQuantity() - qty);
                inventory.setAvailableQuantity(inventory.getAvailableQuantity() + qty);

                if(inventory.getAvailableQuantity()>0)
                {
                    item.getSku().setActive(true);

                }

            }

            order.setOrderstatus(OrderStatus.CANCELLED);

            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "order cancelled",
                    ordersMapper.toDto(order)
            ));

        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage(),
                            null

                    ));
        }


    }

    @Transactional
    public ResponseEntity<ApiResponse<OrderResponsedto>> requestReturn(Long orderId)
    {
        try
        {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            if(order.getOrderstatus() != OrderStatus.CONFIRMED)
            {
                throw new RuntimeException("order cant be returned if not confirmed");
            }

            order.setOrderstatus(OrderStatus.RETURN_REQUESTED);

            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Return requested",
                    ordersMapper.toDto(order)
            ));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Return request Failed",
                            null
                    ));
        }

    }

    @Transactional
    public ResponseEntity<ApiResponse<OrderResponsedto>> approveReturn(Long orderId)
    {
        try
        {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(()-> new RuntimeException("order not found with given orderId:" + orderId));

            if(order.getOrderstatus() != OrderStatus.RETURN_REQUESTED)
            {
                throw new RuntimeException("order cant be approved if not requested");
            }
            order.setOrderstatus(OrderStatus.RETURN_APPROVED);

            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "return approved",
                    ordersMapper.toDto(order)
            ));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to approve return order",
                            null
                    ));
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse<OrderResponsedto>> completeReturn(Long orderId)
    {
        try
        {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("order not found with id: " + orderId));
            if(order.getOrderstatus() != OrderStatus.RETURN_APPROVED)
            {
                throw new RuntimeException("order not approved yet");
            }

            for(OrderItem items : order.getOrderItems())
            {
                Inventory inventory = inventoryRepository.findBySkuIdForUpdate(items.getSku().getId())
                        .orElseThrow();
                int qty = items.getQuantity();
                Integer avail = inventory.getAvailableQuantity() == null ? 0 : inventory.getAvailableQuantity();
                Integer reserved = inventory.getReservedQuantity() == null ? 0 : inventory.getReservedQuantity();
                inventory.setAvailableQuantity(avail + qty);
                inventory.setReservedQuantity(reserved - qty);

                if(inventory.getAvailableQuantity()>0)
                {
                    items.getSku().setActive(true);
                }
            }
            order.setOrderstatus(OrderStatus.RETURNED);
            ordersRepository.save(order);


            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "returned successfully",
                    ordersMapper.toDto(order)
            ));

        }
        catch(Exception e)
        {
           // System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to complete return order",
                            null
                    ));
        }
    }
}
