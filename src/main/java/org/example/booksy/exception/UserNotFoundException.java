package org.example.booksy.exception;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super("User not found.");
    }
}