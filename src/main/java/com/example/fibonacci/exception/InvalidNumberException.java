package com.example.fibonacci.exception;

public class InvalidNumberException extends RuntimeException {
   public InvalidNumberException(String message) {
        super(message);
    }
}
