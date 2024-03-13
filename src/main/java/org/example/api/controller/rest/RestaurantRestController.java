package org.example.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.DeliveryRangeService;
import org.example.business.MenuItemService;
import org.example.domain.Restaurant;
import org.example.domain.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(RestaurantRestController.BASE_PATH)
@Validated
@Tag(name = "Restaurant ")
@SecurityRequirement(name = "BearerAuth")
public class RestaurantRestController {

    public static final String BASE_PATH = "/api";
    public static final String FIND = "/restaurants/find";
    public static final String MENU = "/restaurants/menu";

    private final DeliveryRangeService deliveryRangeService;

    private final MenuItemService menuItemService;
    private final RestaurantMapper restaurantMapper;

    @Operation(
            summary = "Find restaurants that deliver to a given street",
            description = "Provide the name of the street where you want the food delivered. Optionally, you can enter a city.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryRangeDTO.class),
                            examples = @ExampleObject(
                                    name = "findRestaurants",
                                    value = """
                                            {
                                               "city": "",
                                               "street": "ul. Graniczna"
                                            }"""))),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Address updated successfully"
                    )
            }
    )
    @PostMapping(value = FIND)
    public ResponseEntity<List<RestaurantDTO>> findRestaurants(@RequestBody DeliveryRangeDTO deliveryRangeDTO) {
        try {
            List<Restaurant> restaurants = deliveryRangeService.processingFindRestaurants(deliveryRangeDTO);
            List<RestaurantDTO> restaurantDTOS = restaurants.stream()
                    .map(restaurantMapper::map)
                    .toList();
            return new ResponseEntity<>(restaurantDTOS, HttpStatus.OK);
        } catch (CustomException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(
            summary = "Get restaurant menu",
            description = "Enter the restaurant ID and see what menu item offers"
    )
    @GetMapping(value = MENU)
    public ResponseEntity<List<MenuItemDTO>> findMenu(@RequestParam Integer restaurantId) {
        try {
            List<MenuItemDTO> menu = menuItemService.findMenuByRestaurant(restaurantId);

            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (CustomException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
