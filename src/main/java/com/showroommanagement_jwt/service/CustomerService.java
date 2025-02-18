package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.CustomerDetailDTO;
import com.showroommanagement_jwt.entity.Customer;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.exception.UserNameNotFoundException;
import com.showroommanagement_jwt.repository.CustomerRepository;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(final Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer retrieveById(final String id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));

    }

    public List<Customer> retrieveAll() {
        return this.customerRepository.findAll();
    }

    public List<CustomerDetailDTO> retrieveAllCustomerDetail() {
        List<Customer> customer = this.customerRepository.findAll();
        List<CustomerDetailDTO> customerDetailDTOS = new ArrayList<>();
        for (Customer customer1 : customer) {
            CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
            customerDetailDTO.setBrand(customer1.getSalesman().getSalesManager().getShowroom().getBrand());
            customerDetailDTO.setCompanyName(customer1.getSalesman().getSalesManager().getShowroom().getName());
            customerDetailDTO.setEmail(customer1.getEmail());
            customerDetailDTO.setAddress(customer1.getAddress());
            customerDetailDTO.setName(customer1.getName());
            customerDetailDTOS.add(customerDetailDTO);
        }
        return customerDetailDTOS;
    }

    public Customer updateById(final String id, final Customer customer) {
        final Customer customerObject = this.customerRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (customer.getName() != null) {
            customerObject.setName(customer.getName());
        }
        if (customer.getEmail() != null) {
            customerObject.setEmail(customer.getEmail());
        }
        if (customer.getAddress() != null) {
            customerObject.setAddress(customer.getAddress());
        }
        if (customer.getContactNumber() != 0) {
            customerObject.setContactNumber(customer.getContactNumber());
        }
        if (customer.getSalesman() != null) {
            customerObject.setSalesman(customer.getSalesman());
        }
        return this.customerRepository.save(customerObject);
    }

    public String deleteById(final String id) {
        final Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException("Customer not found for this id : " + id));
        this.customerRepository.delete(customer);
        return Constant.REMOVE;
    }
}
