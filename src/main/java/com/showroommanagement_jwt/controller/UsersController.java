package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Users;
import com.showroommanagement_jwt.service.JWTService;
import com.showroommanagement_jwt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTService jwtService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-sales-manager")
    public ResponseDTO createSalesManager(@RequestBody final Users users) {
        return this.usersService.createSalesManager(users);
    }

    @PostMapping("/create-admin")
    public ResponseDTO createAdmin(@RequestBody final Users users) {
        return this.usersService.createAdmin(users);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER')")
    @PostMapping("/create-salesman")
    public ResponseDTO createSalesman(@RequestBody final Users users) {
        return this.usersService.createSalesman(users);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER','ROLE_SALESMAN')")
    @PostMapping("/create-customer")
    public ResponseDTO createCustomer(@RequestBody final Users users) {
        return this.usersService.createCustomer(users);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable final String id) {
        return this.usersService.retrieveById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve")
    public ResponseDTO retrieveAll() {
        return this.usersService.retrieveAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable final String id, final Users users) {
        return this.usersService.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseDTO verifyUser(@RequestBody final Users users) {
        return this.usersService.verifyUser(users);
    }

    @PostMapping("/refresh-token")
    public ResponseDTO refreshToken(@RequestParam final String refreshToken) {
        return this.usersService.refreshToken(refreshToken);
    }
}
