package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Salesman;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.repository.SalesmanRepository;
import com.showroommanagement_jwt.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SalesmanService {
    private final SalesmanRepository salesmanRepository;

    public SalesmanService(SalesmanRepository salesmanRepository) {
        this.salesmanRepository = salesmanRepository;
    }

    @Transactional
    public ResponseDTO createSalesman(final Salesman salesman) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.salesmanRepository.save(salesman));
    }

    public ResponseDTO retrieveById(final String id) {
        if (this.salesmanRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesmanRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesmanRepository.findAll());
    }

    @Transactional
    public ResponseDTO updateById(final String id, final Salesman salesman) {
        final Salesman salesmanObject = this.salesmanRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (salesman.getName() != null) {
            salesmanObject.setName(salesman.getName());
        }
        if (salesman.getAddress() != null) {
            salesmanObject.setAddress(salesman.getAddress());
        }
        if (salesman.getContactNumber() != 0) {
            salesmanObject.setContactNumber(salesman.getContactNumber());
        }
        if (salesman.getExperience() != 0) {
            salesmanObject.setExperience(salesman.getExperience());
        }
        if (salesman.getSalary() != 0) {
            salesmanObject.setSalary(salesman.getSalary());
        }
        if (salesman.getSalesManager() != null) {
            salesmanObject.setSalesManager(salesman.getSalesManager());
        }
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.salesmanRepository.save(salesmanObject));
    }


    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.salesmanRepository.existsById(id)) {
            this.salesmanRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, Constant.DELETE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }
}
