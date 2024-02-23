package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Opinion;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.OpinionEntity;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {
    Role mapFromEntity(RoleEntity role);
    RoleEntity mapToEntity(Role role);




}
