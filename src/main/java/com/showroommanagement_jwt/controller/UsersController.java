package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Users;
import com.showroommanagement_jwt.service.UsersService;
import com.showroommanagement_jwt.util.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/create-sales-manager")
    public ResponseEntity<ResponseDTO> createSalesManager(@RequestBody final Users users) {
        users.setAuthority(Authority.ROLE_SALES_MANAGER);
        ResponseDTO responseDTO = usersService.createUser(users);
        responseDTO.setMessage("Sales Manager Created Successfully");
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

//    @PreAuthorize("hasAuthority('ROLE_SALES_MANAGER')")
    @PostMapping("/create-admin")
    public ResponseEntity<ResponseDTO> createAdmin(@RequestBody final Users users) {
        users.setAuthority(Authority.ROLE_ADMIN);
        ResponseDTO responseDTO = usersService.createUser(users);
        responseDTO.setMessage("Admin Created Successfully");
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER')")
    @PostMapping("/create-salesman")
    public ResponseEntity<ResponseDTO> createSalesman(@RequestBody final Users users) {
        users.setAuthority(Authority.ROLE_SALESMAN);
        ResponseDTO responseDTO = usersService.createUser(users);
        responseDTO.setMessage("Salesman Created Successfully");
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES_MANAGER','ROLE_SALESMAN')")
    @PostMapping("/create-customer")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody final Users users) {
        users.setAuthority(Authority.ROLE_CUSTOMER);
        ResponseDTO responseDTO = usersService.createUser(users);
        responseDTO.setMessage("Customer Created Successfully");
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> verifyUser(@RequestBody final Users users) {
        ResponseDTO responseDTO = usersService.verifyUser(users);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
