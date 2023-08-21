package com.practice.jobsearchproject.exception;

import org.springframework.http.HttpStatus;

public class FileUploadException extends ApplicationException{
    public FileUploadException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
