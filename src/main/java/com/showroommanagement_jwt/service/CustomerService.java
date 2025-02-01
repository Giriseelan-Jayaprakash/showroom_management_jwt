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
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(Constant.CREATE);
        responseDTO.setStatusCode(HttpStatus.CREATED.value());
        responseDTO.setData(this.customerRepository.save(customer));
        return responseDTO;
    }

    public ResponseDTO retrieveById(final Integer id) {
        if (this.customerRepository.existsById(id)) {
            this.customerRepository.findById(id);
            final ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage(Constant.RETRIEVE);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setData(this.customerRepository.findById(id));
            return responseDTO;
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(Constant.RETRIEVE);
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setData(this.customerRepository.findAll());
        return responseDTO;
    }

    public ResponseDTO retrieveAllCustomerDetail() {
        List<Customer> customer = this.customerRepository.findAll();
        List<CustomerDetailDTO> customerDetailDTOS = new ArrayList<>();
        for (Customer customer1 : customer) {
            CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
            customerDetailDTO.setBrand(customer1.getSalesman().getShowroom().getBrand());
            customerDetailDTO.setCompanyName(customer1.getSalesman().getShowroom().getName());
            customerDetailDTO.setEmail(customer1.getEmail());
            customerDetailDTO.setAddress(customer1.getAddress());
            customerDetailDTO.setName(customer1.getName());
            customerDetailDTOS.add(customerDetailDTO);
        }
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(Constant.RETRIEVE);
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setData(customerDetailDTOS);
        return responseDTO;
    }

    @Transactional
    public ResponseDTO updateById(final Integer id, final Customer customer) {
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
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(Constant.UPDATE);
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setData(customerRepository.save(customerObject));
        return responseDTO;
    }

    public ResponseDTO deleteById(final Integer id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.customerRepository.existsById(id)) {
            final ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage(Constant.DELETE);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setData(Constant.REMOVE);
            return responseDTO;
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }
}
