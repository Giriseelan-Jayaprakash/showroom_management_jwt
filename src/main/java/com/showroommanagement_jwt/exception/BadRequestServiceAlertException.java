package com.showroommanagement_jwt.exception;

public class BadRequestServiceAlertException extends RuntimeException {
    public BadRequestServiceAlertException(final String message) {
        super(message);
    }
}
