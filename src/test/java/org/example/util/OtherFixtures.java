package org.example.util;

import org.example.api.dto.*;
import org.example.domain.*;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    public static User someUser2() {
        return User.builder()
                .userId(2)
                .email("alice@example.com")
                .password("password123")
                .active(true)
                .roles(Collections.singleton(Role.builder()
                        .roleId(1)
                        .role("ROLE_CUSTOMER")
                        .build()))
                .build();
    }

    public static User someUser3() {
        return User.builder()
                .userId(2)
                .email("alice@example.com")
                .password("password123")
                .active(true)
                .build();
    }

    public static OrderItemDTO someOrderItemDTO() {
        return OrderItemDTO.builder()
                .menuItemId(someMenuItem().getMenuItemId())
                .quantity(3)
                .build();
    }

    public static OrderItem someOrderItem() {
        return OrderItem.builder()
                .menuItem(someMenuItem())
                .quantity(3)
                .build();
    }

    public static MenuItem someMenuItem() {
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

    public static MenuItemDTO someMenuItemDTO() {
        return MenuItemDTO.builder()
                .id("1")
                .name("Something")
                .category(Category.APPETIZER)
                .description("Description A")
                .price("10")
                .photoUrl("http://www.sth.com/photo")
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

    public static Customer someCustomer() {
        return Customer.builder()
                .customerId(1)
                .name("Alice")
                .surname("Snow")
                .phone("+10 000 000 000")
                .user(someUser2())
                .build();
    }

    public static Restaurant someRestaurant() {
        return Restaurant.builder()
                .restaurantId(1)
                .localName("Restaurant Example")
                .website("www.example.com")
                .phone("+33 333 333 333")
                .email("local@contact.com")
                .openingHours(someOpeningHoursMap())
                .build();
    }
    public static Map<DayOfWeek, OpeningHours> someOpeningHoursMap(){
        Map<DayOfWeek, OpeningHours> openingHoursMap = new TreeMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            OpeningHours openingHours = OpeningHours.builder()
                    .dayOfWeek(day)
                    .openTime(LocalTime.NOON)
                    .closeTime(LocalTime.MIDNIGHT)
                    .deliveryStartTime(LocalTime.NOON)
                    .deliveryEndTime(LocalTime.MIDNIGHT)
                    .build();
            openingHoursMap.put(day, openingHours);
        }
        return openingHoursMap;
    }
    public static RestaurantDTO someRestaurantDTO() {
        return RestaurantDTO.builder()
                .id("1")
                .restaurantName("Restaurant Example")
                .restaurantWebsite("www.example.com")
                .restaurantPhone("+33 333 333 333")
                .restaurantEmail("local@contact.com")
                .addressDTO(someAddressDTO())
                .build();
    }

    public static AddressDTO someAddressDTO() {
        return AddressDTO.builder()
                .addressCountry("Poland")
                .addressCity("Warsaw")
                .addressStreet("Koniecpolska 12")
                .addressPostalCode("00-000")
                .build();
    }

    public static DeliveryRangeDTO someDeliveryRangeDTO() {
        return DeliveryRangeDTO.builder()
                .restaurant("restaurant")
                .city("city")
                .street("street")
                .build();
    }

    public static DeliveryRange someDeliveryRange() {
        return DeliveryRange.builder()
                .deliveryRangeId(1L)
                .city("city")
                .street("street")
                .build();
    }

    public static Orders someOrder() {
        return Orders.builder()
                .orderId(1)
                .orderNumber(20240312222L)
                .orderDate(OffsetDateTime.now())
                .restaurant(someRestaurant())
                .customer(someCustomer())
                .orderItems(List.of(someOrderItem()))
                .status("PENDING")
                .build();

    }

    public static OpeningHours someOpeningHours() {
        return OpeningHours.builder()
                .build();
    }

    public static Address someAddress() {
        return Address.builder()
                .country("Poland")
                .city("Warsaw")
                .street("Koniecpolska 12")
                .postalCode("00-000")
                .build();
    }

    public static RegistrationDTO someRegistrationDTO() {
        return RegistrationDTO.builder()
                .name("Stefan")
                .surname("Zajavka")
                .phone("+48 000 000 000")
                .email("test@test.com")
                .password("password")
                .matchingPassword("password")
                .addressDTO(someAddressDTO())
                .build();
    }

    public static UserDetails someUserDetails() {
        return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username("john@example.com")
                .password("password123")
                .roles("OWNER")
                .build();
    }
}
