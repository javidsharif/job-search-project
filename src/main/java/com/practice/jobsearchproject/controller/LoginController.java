package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    @GetMapping
    public Object login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                if (userDetails.getUserAuthentication().getCompany() != null) {
                    return companyMapper.convertToCompanyResponseDto(userDetails.getUserAuthentication().getCompany());
                } else {
                    return userMapper.toUserResponse(userDetails.getUserAuthentication().getUser());
                }
            }
        }
        return null;
    }
}
