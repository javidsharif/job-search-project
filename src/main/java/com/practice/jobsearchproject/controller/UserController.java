package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
import com.practice.jobsearchproject.model.dto.response.UserResponse;
import com.practice.jobsearchproject.model.mapper.UserMapper;
import com.practice.jobsearchproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRequestDto userDto) {
        userService.createUser(userDto);
    }
    @PutMapping
    public void updateUser(@Valid @RequestBody UserRequestDto userDto,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
    userService.updateUser(userDto, userDetails);

    }

}
