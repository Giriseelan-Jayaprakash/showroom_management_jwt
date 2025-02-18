package com.showroommanagement_jwt.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String message) {
        super(message);
    }
}
