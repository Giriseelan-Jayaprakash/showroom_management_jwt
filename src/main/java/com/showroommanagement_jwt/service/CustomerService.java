package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.CustomerDetailDTO;
import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Customer;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.repository.CustomerRepository;
import com.showroommanagement_jwt.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public ResponseDTO createCustomer(final Customer customer) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.customerRepository.save(customer));
    }

    public ResponseDTO retrieveById(final String id) {
        if (this.customerRepository.existsById(id)) {
            this.customerRepository.findById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.customerRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.customerRepository.findAll());
    }

    public ResponseDTO retrieveAllCustomerDetail() {
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
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, customerDetailDTOS);
    }

    @Transactional
    public ResponseDTO updateById(final String id, final Customer customer) {
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
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, customerRepository.save(customerObject));
    }

    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.customerRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, Constant.REMOVE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }
}
