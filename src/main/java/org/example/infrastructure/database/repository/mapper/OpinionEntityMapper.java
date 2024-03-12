package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Opinion;
import org.example.infrastructure.database.entity.OpinionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OpinionEntityMapper {
   Opinion mapFromEntity(OpinionEntity opinionEntity);

   OpinionEntity mapToEntity(Opinion opinion);
}
