package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerRequestDTO {

    private String customerName;
    private String customerSurname;
    private String customerPhone;
    private String customerEmail;
    private String customerAddressCountry;
    private String customerAddressCity;
    private String customerAddressPostalCode;
    private String customerAddressStreet;

    private String menuItemName;
    private String menuItemCategory;
    private String menuItemIdPrice;

    private Integer orderItemQuantity;



}
