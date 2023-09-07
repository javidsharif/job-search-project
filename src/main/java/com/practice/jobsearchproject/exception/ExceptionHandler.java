package com.practice.jobsearchproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(parseFieldErrors(e.getFieldErrors()), HttpStatus.BAD_REQUEST);
    }

    private List<String> parseFieldErrors(List<FieldError> fieldErrors) {
        ArrayList<String> fieldError = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            fieldError.add(error.getDefaultMessage());
        }
        return fieldError;
    }
}
