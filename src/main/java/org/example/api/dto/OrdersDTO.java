package org.example.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {


    String orderDate;
    Long orderNumber;
    String status;
    String deliveryTime;
    Integer customerId;
    Integer restaurantId;
    List<OrderItemDTO> orderItems;
}
