package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Customer;
import com.showroommanagement_jwt.service.CustomerService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseDTO createCustomer(@RequestBody final Customer customer) {
        return this.customerService.createCustomer(customer);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final Integer id) {
        return this.customerService.retrieveById(id);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieveAll() {
        return this.customerService.retrieveAll();
    }

    @GetMapping("/retrieve-all-customer")
    public ResponseDTO retrieveAllCustomerDetail() {
        return this.customerService.retrieveAllCustomerDetail();
    }

    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final Integer id, @RequestBody final Customer customer) {
        return this.customerService.updateById(id, customer);
    }

    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final Integer id) {
        return this.customerService.deleteById(id);
    }
}
