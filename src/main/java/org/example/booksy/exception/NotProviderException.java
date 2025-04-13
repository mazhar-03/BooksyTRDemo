package org.example.booksy.exception;

public class NotProviderException extends CustomException {
    public NotProviderException() {
        super("Only providers can create profiles.");
    }
}