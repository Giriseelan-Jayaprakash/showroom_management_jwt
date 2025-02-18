package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.User;
import com.showroommanagement_jwt.service.UserService;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-sales-manager")
    public ResponseDTO createSalesManager(@RequestBody final User user) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.userService.createSalesManager(user));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-admin")
    public ResponseDTO createAdmin(@RequestBody final User user) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.userService.createAdmin(user));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER')")
    @PostMapping("/create-salesman")
    public ResponseDTO createSalesman(@RequestBody final User user) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.userService.createSalesman(user));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER','ROLE_SALESMAN')")
    @PostMapping("/create-customer")
    public ResponseDTO createCustomer(@RequestBody final User user) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.userService.createCustomer(user));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.userService.retrieveById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve")
    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.userService.retrieveAll());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable final String id, final User user) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, this.userService.deleteById(id));
    }


    @PostMapping("/login")
    public ResponseDTO verifyUser(@RequestBody final User user) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.LOG_IN, this.userService.verifyUser(user));
    }

    @PostMapping("/refresh-token")
    public ResponseDTO refreshToken(@RequestParam final String refreshToken) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.TOKEN, this.userService.refreshToken(refreshToken));
    }
}
