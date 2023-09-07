package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.ResetPasswordDto;
import com.practice.jobsearchproject.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {
    private final UserAuthenticationService userAuthenticationService;

    @PutMapping("/reset-password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        return ResponseEntity.ok(userAuthenticationService.setPassword(resetPasswordDto));
    }
}
