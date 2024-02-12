package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    String orderDate;
    Long orderNumber;
    String status;
    String deliveryTime;
    String customer;
    String restaurant;
    String menuItem;

}
