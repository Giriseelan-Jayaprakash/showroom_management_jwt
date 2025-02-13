package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.UserCredential;
import com.showroommanagement_jwt.service.JWTService;
import com.showroommanagement_jwt.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserCredentialController {
    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private JWTService jwtService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-sales-manager")
    public ResponseDTO createSalesManager(@RequestBody final UserCredential userCredential) {
        return this.userCredentialService.createSalesManager(userCredential);
    }

    @PostMapping("/create-admin")
    public ResponseDTO createAdmin(@RequestBody final UserCredential userCredential) {
        return this.userCredentialService.createAdmin(userCredential);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER')")
    @PostMapping("/create-salesman")
    public ResponseDTO createSalesman(@RequestBody final UserCredential userCredential) {
        return this.userCredentialService.createSalesman(userCredential);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER','ROLE_SALESMAN')")
    @PostMapping("/create-customer")
    public ResponseDTO createCustomer(@RequestBody final UserCredential userCredential) {
        return this.userCredentialService.createCustomer(userCredential);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable final String id) {
        return this.userCredentialService.retrieveById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve")
    public ResponseDTO retrieveAll() {
        return this.userCredentialService.retrieveAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable final String id, final UserCredential userCredential) {
        return this.userCredentialService.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseDTO verifyUser(@RequestBody final UserCredential userCredential) {
        return this.userCredentialService.verifyUser(userCredential);
    }

    @PostMapping("/refresh-token")
    public ResponseDTO refreshToken(@RequestParam final String refreshToken) {
        return this.userCredentialService.refreshToken(refreshToken);
    }
}
