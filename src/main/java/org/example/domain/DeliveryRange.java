package org.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.example.infrastructure.database.entity.RestaurantEntity;

import java.util.ArrayList;
import java.util.List;
@Value
@With
@Builder
@EqualsAndHashCode(of = "deliveryRangeId")
@ToString(of = {"deliveryRangeId", "city", "street", "restaurants"})
public class DeliveryRange {

     Long deliveryRangeId;

     String city;

     String street;

     List<Restaurant> restaurants;

}
