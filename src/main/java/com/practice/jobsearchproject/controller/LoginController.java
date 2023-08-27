package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.model.mapper.UserMapper;
import com.practice.jobsearchproject.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class LoginController {
//    private final UserMapper userMapper;
//    private final CompanyMapper companyMapper;
    private final UserAuthenticationService userAuthenticationService;

//    @GetMapping
//    public Object login(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            if (userDetails != null) {
//                if (userDetails.getUserAuthentication().getCompany() != null) {
//                    return companyMapper.convertToCompanyResponseDto(userDetails.getUserAuthentication().getCompany());
//                } else {
//                    return userMapper.toUserResponse(userDetails.getUserAuthentication().getUser());
//                }
//            }
//        }
//        return null;
//    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userAuthenticationService.login(authenticationRequest));
    }
}
