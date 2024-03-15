package org.example.util;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.OrderItemDTO;
import org.example.domain.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OtherFixtures {

    public static LoginDTO someLoginExample() {
        return LoginDTO.builder()
                .email("test@test.com")
                .password("password")
                .build();
    }

    public static User someUser1() {
        return User.builder()
                .userId(1)
                .email("john@example.com")
                .password("password123")
                .active(true)
                .roles(Collections.singleton(Role.builder()
                        .roleId(1)
                        .role("ROLE_OWNER")
                        .build()))
                .build();
    }
    public static OrderItemDTO someOrderItem(){
        return OrderItemDTO.builder()
                .menuItemId(someMenuItem().getMenuItemId())
                .quantity(3)
                .build();
    }
    public static MenuItem someMenuItem(){
        return MenuItem.builder()
                .menuItemId(1)
                .name("Something")
                .category("Category A")
                .description("Description A")
                .price(BigDecimal.TEN)
                .photoUrl("www.sth.com/photo")
                .restaurant(someRestaurant())
                .build();
    }
    public static MenuItemDTO someMenuItemDTO(){
        return MenuItemDTO.builder()
                .id("1")
                .name("Something")
                .category(Category.APPETIZER)
                .description("Description A")
                .price("10")
                .photoUrl("www.sth.com/photo")
                .restaurantsList(someRestaurant().getLocalName())
                .build();
    }
    public static Owner someOwner() {
        return Owner.builder()
                .ownerId(1)
                .name("John")
                .surname("Malkovic")
                .phone("+10 000 000 000")
                .user(someUser1())
                .build();
    }

    public static Restaurant someRestaurant() {
        return Restaurant.builder()
                .restaurantId(1)
                .localName("Restaurant Example")
                .website("www.example.com")
                .phone("+33 333 333 333")
                .email("local@contact.com")
                .build();
    }

    public static DeliveryRangeDTO someDeliveryRangeDTO() {
        return DeliveryRangeDTO.builder()
                .restaurant("restaurant")
                .city("city")
                .street("street")
                .build();
    }
}
