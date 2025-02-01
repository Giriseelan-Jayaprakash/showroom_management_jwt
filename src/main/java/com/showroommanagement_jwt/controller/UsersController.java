package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Users;
import com.showroommanagement_jwt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-user/demo")
    public ResponseDTO createUser(@RequestBody final Users users) {
        return usersService.createUser(users);
    }

    @PostMapping("/login")
    public ResponseDTO verifyUser(@RequestBody final Users users) {
        return usersService.verifyUser(users);
    }
}
