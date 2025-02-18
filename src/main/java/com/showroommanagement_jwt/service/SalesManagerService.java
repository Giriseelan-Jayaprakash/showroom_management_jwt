package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.entity.SalesManager;
import com.showroommanagement_jwt.exception.UserNameNotFoundException;
import com.showroommanagement_jwt.repository.SalesManagerRepository;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesManagerService {
    private final SalesManagerRepository salesManagerRepository;

    public SalesManagerService(SalesManagerRepository salesManagerRepository) {
        this.salesManagerRepository = salesManagerRepository;
    }

    public SalesManager createSalesManager(final SalesManager salesManager) {
        return this.salesManagerRepository.save(salesManager);
    }

    public SalesManager retrieveById(final String id) {
        return this.salesManagerRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<SalesManager> retrieveALl() {
        return this.salesManagerRepository.findAll();
    }

    public SalesManager updateById(final String id, final SalesManager salesManager) {
        final SalesManager salesManagerObject = this.salesManagerRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
        if (salesManager.getName() != null) {
            salesManagerObject.setName(salesManager.getName());
        }
        if (salesManager.getAddress() != null) {
            salesManagerObject.setAddress(salesManager.getAddress());
        }
        if (salesManager.getContactNumber() != 0) {
            salesManagerObject.setContactNumber(salesManager.getContactNumber());
        }
        if (salesManager.getShowroom() != null) {
            salesManagerObject.setShowroom(salesManager.getShowroom());
        }
        return this.salesManagerRepository.save(salesManagerObject);
    }

    public String deleteById(final String id) {
        final SalesManager salesManager = this.salesManagerRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException("Sales Manager not found for this id : " + id));
        this.salesManagerRepository.delete(salesManager);
        return Constant.REMOVE;
    }
}
