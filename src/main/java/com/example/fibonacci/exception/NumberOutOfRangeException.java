package com.example.fibonacci.exception;

public class NumberOutOfRangeException extends RuntimeException {
    public NumberOutOfRangeException(String message) {
        super(message);
    }
}