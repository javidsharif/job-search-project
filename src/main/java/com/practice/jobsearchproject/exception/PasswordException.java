package com.practice.jobsearchproject.exception;

import org.springframework.http.HttpStatus;

public class PasswordException extends ApplicationException {
    public PasswordException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
