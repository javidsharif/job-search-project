package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
import com.practice.jobsearchproject.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUser(UserRequestDto userDto);

    void updateUser(UserRequestDto userDto, CustomUserDetails userDetails);
}
