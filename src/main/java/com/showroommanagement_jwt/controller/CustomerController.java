package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Customer;
import com.showroommanagement_jwt.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody final Customer customer) {
        ResponseDTO responseDTO = this.customerService.createCustomer(customer);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseEntity<ResponseDTO> retrieveById(@PathVariable("id") final String id) {
        ResponseDTO responseDTO = this.customerService.retrieveById(id);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN')")
    @GetMapping("/retrieve")
    public ResponseEntity<ResponseDTO> retrieveAll() {
        ResponseDTO responseDTO = this.customerService.retrieveAll();
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN')")
    @GetMapping("/retrieve-all-customer")
    public ResponseEntity<ResponseDTO> retrieveAllCustomerDetail() {
        ResponseDTO responseDTO = this.customerService.retrieveAllCustomerDetail();
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable("id") final String id, @RequestBody final Customer customer) {
        ResponseDTO responseDTO = this.customerService.updateById(id, customer);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable("id") final String id) {
        ResponseDTO responseDTO = this.customerService.deleteById(id);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
