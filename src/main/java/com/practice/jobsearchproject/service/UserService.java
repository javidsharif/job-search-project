package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
import com.practice.jobsearchproject.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

//    AuthenticationResponse createUser(UserRequestDto userDto);
AuthenticationResponse createUser(UserRequestDto userDto, MultipartFile file) throws IOException;

    void updateUser(UserRequestDto userDto, MultipartFile file, CustomUserDetails userDetails) throws IOException;
}
