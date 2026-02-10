package com.example.Catalog.Managment.Dto.Response;

import com.example.Catalog.Managment.Dto.BillitemDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BillResponseDto {

    @NotNull(message = "Order id cannot be null")
    private Long orderId;

    @NotBlank(message = "Order status required")
    private String orderStatus;

    @NotNull(message = "Order date required")
    private LocalDate orderDate;

    @NotBlank(message = "Customer name required")
    private String customerName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Customer email required")
    private String customerEmail;

    @NotEmpty(message = "Bill must contain items")
    @Valid
    private List<BillitemDto> items;

    @NotNull(message = "Subtotal required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Subtotal must be > 0")
    private BigDecimal subtotal;

    @NotNull(message = "Tax required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tax cannot be negative")
    private BigDecimal tax;

    @NotNull(message = "Grand total required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Grand total must be > 0")
    private BigDecimal grandTotal;
}
