package ru.netology.exceptions;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("Not authorized!");
    }
}
