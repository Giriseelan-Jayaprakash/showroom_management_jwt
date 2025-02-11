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
    public ResponseDTO createCustomer(@RequestBody final Customer customer) {
        return this.customerService.createCustomer(customer);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return this.customerService.retrieveById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN')")
    @GetMapping("/retrieve")
    public ResponseDTO retrieveAll() {
        return this.customerService.retrieveAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN')")
    @GetMapping("/retrieve-all-customer")
    public ResponseDTO retrieveAllCustomerDetail() {
        return this.customerService.retrieveAllCustomerDetail();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final String id, @RequestBody final Customer customer) {
        return this.customerService.updateById(id, customer);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return this.customerService.deleteById(id);
    }
}
