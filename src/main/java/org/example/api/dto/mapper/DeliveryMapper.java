package org.example.api.dto.mapper;

import org.example.api.dto.DeliveryRangeDTO;
import org.example.domain.DeliveryRange;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryRangeDTO map(String city, String restaurant, List<String> streets);

}
