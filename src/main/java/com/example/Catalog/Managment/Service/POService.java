package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Entity.Inventory;
import com.example.Catalog.Managment.Entity.PO;
import com.example.Catalog.Managment.Enum.OrderStatus;
import com.example.Catalog.Managment.Repository.InventoryRepository;
import com.example.Catalog.Managment.Repository.PORepository;
import com.example.Catalog.Managment.Response.ApiResponse;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

@Slf4j
public class POService {

    private final PORepository poRepository;
    private final InventoryRepository inventoryRepository;

    public ResponseEntity<ApiResponse<String>> createPO(Long orderId, OrderStatus status) {

        try {

            if (status != OrderStatus.PURCHASED_CONFIRM) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(

                        new ApiResponse<>(false,
                                HttpStatus.BAD_REQUEST.value(),
                                "Status is not PURCHASED_CONFIRM. PO not created.",
                                null)
                );
            }


            PO po = new PO();
            po.setOrderId(orderId);
            po.setOrderstatus(OrderStatus.PURCHASED_CONFIRM);

            poRepository.save(po);

            log.info("PO created: {}", po);
            Inventory inventory = inventoryRepository.findById(orderId)
                    .orElseThrow(()-> new RuntimeException("inventory not found for given id"));
            inventory.setPo(po);
            inventoryRepository.save(inventory);

            return ResponseEntity.ok(
                    new ApiResponse<>(true,
                            HttpStatus.OK.value(),
                            "PO created successfully",
                            null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", null
                    )
            );
        }
    }
}


