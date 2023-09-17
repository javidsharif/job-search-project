package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
import com.practice.jobsearchproject.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class LoginController {
    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userAuthenticationService.login(authenticationRequest));
    }
}
