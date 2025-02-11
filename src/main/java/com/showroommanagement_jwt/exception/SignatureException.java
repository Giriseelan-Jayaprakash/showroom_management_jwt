package com.showroommanagement_jwt.exception;

public class SignatureException extends RuntimeException {
    public SignatureException(String message) {
        super(message);
    }
}
