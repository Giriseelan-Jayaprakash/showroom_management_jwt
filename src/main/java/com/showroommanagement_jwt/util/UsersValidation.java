package com.showroommanagement_jwt.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UsersValidation {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email.trim());
    }

    public boolean isValidPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }
//    public void areCredentialsValid(String emailId, String password) {
//        if (!isValidEmail(emailId)) throw new BadRequestServiceAlertException("Email is not a valid format");
//        if (!isValidPassword(password)) throw new BadRequestServiceAlertException("Password is not a valid format");
//
//    }
}