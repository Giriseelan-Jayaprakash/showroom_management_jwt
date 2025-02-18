package com.showroommanagement_jwt.exception;

public class JWTSignatureException extends Exception {
    public JWTSignatureException(final String message) {
        super(message);
    }
}
