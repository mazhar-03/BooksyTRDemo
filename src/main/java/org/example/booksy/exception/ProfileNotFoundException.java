package org.example.booksy.exception;

public class ProfileNotFoundException extends CustomException {
    public ProfileNotFoundException() {
        super("Profile not found for this user.");
    }
}

