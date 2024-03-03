package org.example.infrastructure.database.repository.mapper;

import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface  OpeningHoursMapper {


    OpeningHours mapFromEntity(OpeningHoursEntity entity);




    OpeningHoursEntity mapToEntity(OpeningHours openingHours);


}
