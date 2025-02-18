package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.entity.Salesman;
import com.showroommanagement_jwt.exception.UserNameNotFoundException;
import com.showroommanagement_jwt.repository.SalesmanRepository;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesmanService {
    private final SalesmanRepository salesmanRepository;

    public SalesmanService(SalesmanRepository salesmanRepository) {
        this.salesmanRepository = salesmanRepository;
    }

    public Salesman createSalesman(final Salesman salesman) {
        return this.salesmanRepository.save(salesman);
    }

    public Salesman retrieveById(final String id) {
        return this.salesmanRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Salesman> retrieveAll() {
        return this.salesmanRepository.findAll();
    }

    public Salesman updateById(final String id, final Salesman salesman) {
        final Salesman salesmanObject = this.salesmanRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
        if (salesman.getName() != null) {
            salesmanObject.setName(salesman.getName());
        }
        if (salesman.getAddress() != null) {
            salesmanObject.setAddress(salesman.getAddress());
        }
        if (salesman.getContactNumber() != null) {
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
        return this.salesmanRepository.save(salesmanObject);
    }


    public String deleteById(final String id) {
        final Salesman salesman = this.salesmanRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException("Salesman not found for this id : " + id));
        this.salesmanRepository.delete(salesman);
        return Constant.REMOVE;
    }
}
