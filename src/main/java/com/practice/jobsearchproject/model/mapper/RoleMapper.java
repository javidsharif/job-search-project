package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.RoleDto;
import com.practice.jobsearchproject.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toRoleDto(Role role);
}
