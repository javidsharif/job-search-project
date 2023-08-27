//package com.practice.jobsearchproject.controller;
//
//import com.practice.jobsearchproject.service.MailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.mail.MessagingException;
//
//@RestController
//@RequestMapping("/api/mail")
//@RequiredArgsConstructor
//public class MailController {
//    private final MailService mailService;
//
//
//    @PostMapping("/reset-password-email")
//    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
//        return new ResponseEntity<>(mailService.createResetPasswordEmail(email), HttpStatus.OK);
//    }
//
////    @PostMapping("/reset-password-email")
////    public ResponseEntity<String> sendResetPasswordEmail(@RequestBody UserDto userDto) {
////        return ResponseEntity.ok(SuccessResponseDto.of(emailService.createResetPasswordEmail(userDto)));
////    }
//}
