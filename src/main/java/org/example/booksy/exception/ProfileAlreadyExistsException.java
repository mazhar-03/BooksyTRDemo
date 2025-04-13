package org.example.booksy.exception;

public class ProfileAlreadyExistsException extends CustomException {
    public ProfileAlreadyExistsException() {
        super("Profile already exists for this user.");
    }
}
