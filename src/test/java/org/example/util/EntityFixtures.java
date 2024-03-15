package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.api.dto.OpeningHoursDTO;
import org.example.domain.OpeningHours;
import org.example.infrastructure.database.entity.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@UtilityClass
public class EntityFixtures {
    public static UserEntity someUser1() {
        return UserEntity.builder()
                .userId(1)
                .email("john@example.com")
                .password("password123")
                .active(true)
                .roles(Collections.singleton(RoleEntity.builder()
                                .roleId(1)
                        .role("ROLE_OWNER")
                        .build()))
                .build();
    }
    public static UserEntity someUser2() {
        return UserEntity.builder()
                .userId(2)
                .email("jane@example.com")
                .password("password456")
                .active(true)
                .roles(Collections.singleton(RoleEntity.builder()
                                .roleId(1)
                        .role("ROLE_OWNER")
                        .build()))
                .build();
    }
    public static UserEntity someUser3() {
        return UserEntity.builder()
                .userId(3)
                .email("alice@example.com")
                .password("password123")
                .active(true)
                .roles(Collections.singleton(RoleEntity.builder()
                                .roleId(2)
                        .role("ROLE_CUSTOMER")
                        .build()))
                .build();
    }
    public static UserEntity someUser4() {
        return UserEntity.builder()
                .userId(4)
                .email("bob@example.com")
                .password("password123")
                .active(true)
                .roles(Collections.singleton(RoleEntity.builder()
                                .roleId(2)
                        .role("ROLE_CUSTOMER")
                        .build()))
                .build();
    }

    public static AddressEntity someAddress1() {
        return AddressEntity.builder()
                .addressId(1)
                .country("USA")
                .city("New York")
                .street("Broadway")
                .postalCode("10001")
                .build();
    }

    public static AddressEntity someAddress2() {
        return AddressEntity.builder()
                .addressId(2)
                .country("UK")
                .city("London")
                .street("Westminster")
                .postalCode("SW1A 1AA")
                .build();
    }

    public static AddressEntity someAddress3() {
        return AddressEntity.builder()
                .addressId(3)
                .country("USA")
                .city("Las Vegas")
                .street("Casino Royal")
                .postalCode("10002")
                .build();
    }

    public static AddressEntity someAddress4() {
        return AddressEntity.builder()
                .addressId(4)
                .country("UK")
                .city("London")
                .street("Short Way")
                .postalCode("SW1A 1AA")
                .build();
    }
    public static OwnerEntity someOwner1() {
        return OwnerEntity.builder()
                .ownerId(1)
                .name("John")
                .surname("Doe")
                .phone("+22 222 222 222")
                .user(someUser1())
                .build();
    }

    public static OwnerEntity someOwner2() {
        return OwnerEntity.builder()
                .ownerId(2)
                .name("Jane")
                .surname("Smith")
                .phone("+33 333 333 333")
                .user(someUser2())
                .build();
    }

    public static CustomerEntity someCustomer1() {
        return CustomerEntity.builder()
                .customerId(1)
                .name("Alice")
                .surname("Johnson")
                .phone("+00 000 000 000")
                .user(someUser3())
                .address(someAddress1())
                .build();
    }

    public static CustomerEntity someCustomer2() {
        return CustomerEntity.builder()
                .customerId(2)
                .name("Bob")
                .surname("Smith")
                .phone("+11 111 111 111")
                .user(someUser4())
                .address(someAddress2())
                .build();
    }

    public static RestaurantEntity someRestaurant1() {
        return RestaurantEntity.builder()
                .restaurantId(1)
                .localName("Restaurant A")
                .phone("+33 123 456 890")
                .website("www.restaurantA.com")
                .email("info@restaurantA.com")
                .owner(someOwner1())
                .address(someAddress3())
                .build();
    }
    public static RestaurantEntity someRestaurant2() {
        return RestaurantEntity.builder()
                .restaurantId(2)
                .localName("Restaurant B")
                .phone("+22 987 654 320")
                .website("www.restaurantB.com")
                .email("info@restaurantB.com")
                .owner(someOwner2())
                .address(someAddress4())
                .build();
    }
    public static Map<DayOfWeek, OpeningHoursEntity> someOpeningHours() {
        Map<DayOfWeek, OpeningHoursEntity> openingHoursMap = new TreeMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            OpeningHoursEntity openingHours = OpeningHoursEntity.builder()
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

    public static OrdersEntity someOrder1() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 3, 15,10,0,0,0);
        return OrdersEntity.builder()
                .orderId(1)
                .customer(someCustomer1())
                .orderDate(OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(1)))
                .orderNumber(2024030874121L)
                .status("PENDING")
                .deliveryTime(LocalTime.of(12,0,0))
                .restaurant(someRestaurant1())
                .build();
    }
    public static OrdersEntity someOrder2() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 3, 15,11,0,0,0);
        return OrdersEntity.builder()
                .orderId(2)
                .customer(someCustomer2())
                .orderDate(OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(1)))
                .orderNumber(2024030874122L)
                .status("COMPLETED")
                .restaurant(someRestaurant2())
                .build();
    }
    public static MenuItemEntity someMenuItem1() {
        return MenuItemEntity.builder()
                .menuItemId(1)
                .name("Item 1")
                .category("Category A")
                .description("Description A")
                .price(BigDecimal.valueOf(10.99))
                .photoUrl("url1")
                .restaurant(someRestaurant1())
                .build();
    }
    public static MenuItemEntity someMenuItem2() {
        return MenuItemEntity.builder()
                .menuItemId(2)
                .name("Item 2")
                .category("Category B")
                .description("Description B")
                .price(BigDecimal.valueOf(8.50))
                .photoUrl("url2")
                .restaurant(someRestaurant1())
                .build();
    }
    public static MenuItemEntity someMenuItem3() {
        return MenuItemEntity.builder()
                .menuItemId(3)
                .name("Item 3")
                .category("Category C")
                .description("Description C")
                .price(BigDecimal.valueOf(12.75))
                .photoUrl("url3")
                .restaurant(someRestaurant2())
                .build();
    }
    public static MenuItemEntity someMenuItem4() {
        return MenuItemEntity.builder()
                .menuItemId(4)
                .name("Item 4")
                .category("Category D")
                .description("Description D")
                .price(BigDecimal.valueOf(9.99))
                .photoUrl("url4")
                .restaurant(someRestaurant2())
                .build();
    }
    public static OrderItemEntity someOrderItem1() {
        return OrderItemEntity.builder()
                .orderItemId(1)
                .menuItem(someMenuItem1())
                .quantity(2)
                .order(someOrder1())
                .build();
    }

    public static OrderItemEntity someOrderItem2() {
        return OrderItemEntity.builder()
                .orderItemId(2)
                .menuItem(someMenuItem2())
                .quantity(1)
                .order(someOrder1())
                .build();
    }
    public static OrderItemEntity someOrderItem3() {
        return OrderItemEntity.builder()
                .orderItemId(3)
                .menuItem(someMenuItem3())
                .quantity(3)
                .order(someOrder2())
                .build();
    }

    public static OrderItemEntity someOrderItem4() {
        return OrderItemEntity.builder()
                .orderItemId(4)
                .menuItem(someMenuItem4())
                .quantity(2)
                .order(someOrder2())
                .build();
    }

}
