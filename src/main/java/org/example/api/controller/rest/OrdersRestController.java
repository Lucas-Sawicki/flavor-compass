package org.example.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.api.dto.OrdersDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.api.dto.rest.RestRestaurantUpdateDTO;
import org.example.business.OrdersService;
import org.example.business.OwnerService;
import org.example.business.UserService;
import org.example.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = OrdersRestController.BASE_PATH)
@Tag(name = "Find orders")
@SecurityRequirement(name = "BearerAuth")
public class OrdersRestController {
    public static final String BASE_PATH = "/order";
    private static final String ORDERS = "/all";
    private final OrdersService ordersService;
    private final OrdersMapper ordersMapper;
    private final OwnerService ownerService;
    private final UserService userService;

    @Operation(
            summary = "Get all orders for your account",
            description = "Only available for customer and owner roles"
    )
    @GetMapping(value = ORDERS)
    public ResponseEntity<List<OrdersDTO>> getOrders(Principal principal) {
        String email = principal.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            Set<Role> roles = user.get().getRoles();
            List<Orders> orders;
            if (roles.stream().anyMatch(role -> role.getRole().equals("ROLE_OWNER"))) {
                Owner owner = ownerService.findOwnerByEmail(email);
                orders = ordersService.ordersHistoryForOwner(owner);
            } else if (roles.stream().anyMatch(role -> role.getRole().equals("ROLE_CUSTOMER"))) {
                Customer customer = user.get().getCustomer();
                orders = ordersService.ordersHistoryForCustomer(customer);
            } else {
                return ResponseEntity.noContent().build();
            }
            List<OrdersDTO> ordersDTOS = orders.stream()
                    .map(ordersMapper::map)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(ordersDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
