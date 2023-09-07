package com.practice.jobsearchproject.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {
    String createResetPasswordEmail(String mail) throws MessagingException;
}
