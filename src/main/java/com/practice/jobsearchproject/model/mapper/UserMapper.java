package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.UserResponse;
import com.practice.jobsearchproject.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
