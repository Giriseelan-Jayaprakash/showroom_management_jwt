package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.entity.Users;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/v1/users"))
public class TestController {
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-user")
    public Users print() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return (Users) principal;
    }
}
