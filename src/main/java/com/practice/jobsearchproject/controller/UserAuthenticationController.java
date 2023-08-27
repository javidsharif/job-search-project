//package com.practice.jobsearchproject.controller;
//
//import com.practice.jobsearchproject.model.dto.ResetPasswordDto;
//import com.practice.jobsearchproject.service.UserAuthenticationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//public class UserAuthenticationController {
//    private final UserAuthenticationService userAuthenticationService;
//    @PutMapping("/set-password")
//    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestHeader String newPassword) {
//        return new ResponseEntity<>(userService.setPassword(email, newPassword), HttpStatus.OK);
//    }
//
//    @PutMapping("/reset-password")
//    public ResponseEntity<String> updatePassword(@RequestBody ResetPasswordDto resetPasswordDto) {
//        return ResponseEntity.ok(userAuthenticationService.setPassword(resetPasswordDto.getVerificationToken(), resetPasswordDto.getPassword()));
//    }
//}
