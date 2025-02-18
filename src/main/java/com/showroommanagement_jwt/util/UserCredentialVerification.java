package com.showroommanagement_jwt.util;

import com.showroommanagement_jwt.entity.User;
import com.showroommanagement_jwt.service.JWTService;
import com.showroommanagement_jwt.service.MyUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialVerification {

    private final HttpServletRequest httpServletRequest;
    private final JWTService jwtService;
    private final MyUserDetailsService myUserDetailsService;

    public UserCredentialVerification(final HttpServletRequest httpServletRequest, final JWTService jwtService, final MyUserDetailsService myUserDetailsService) {
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }

    public User userInfo() {
        final String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            final String username = jwtService.extractUserName(token);
            return (User) myUserDetailsService.loadUserByUsername(username);
        }
        return null;
    }
}
