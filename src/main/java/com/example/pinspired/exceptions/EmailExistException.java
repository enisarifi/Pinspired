package com.example.pinspired.exceptions;

public class EmailExistException extends RuntimeException {
    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException() {
        super("Email already exists");
    }
}
