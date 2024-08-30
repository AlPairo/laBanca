package com.example.fibonacci.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidNumberException.class)
    public ResponseEntity<String> handleInvalidNumberException(InvalidNumberException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NumberOutOfRangeException.class)
    public ResponseEntity<String> handleNumberOutOfRangeException(NumberOutOfRangeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

