package com.practice.jobsearchproject.exception;

import org.springframework.http.HttpStatus;

public class RequestFailedException extends ApplicationException {

    public RequestFailedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
