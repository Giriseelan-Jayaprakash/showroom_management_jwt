package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.SalesManager;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.repository.SalesManagerRepository;
import com.showroommanagement_jwt.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SalesManagerService {
    private final SalesManagerRepository salesManagerRepository;

    public SalesManagerService(SalesManagerRepository salesManagerRepository) {
        this.salesManagerRepository = salesManagerRepository;
    }

    @Transactional
    public ResponseDTO createSalesManager(final SalesManager salesManager) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.salesManagerRepository.save(salesManager));
    }

    public ResponseDTO retrieveById(final String id) {
        if (this.salesManagerRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesManagerRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveALl() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesManagerRepository.findAll());
    }

    @Transactional
    public ResponseDTO updateById(final String id, final SalesManager salesManager) {
        final SalesManager salesManagerObject = this.salesManagerRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
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
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.salesManagerRepository.save(salesManagerObject));
    }

    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.salesManagerRepository.existsById(id)) {
            this.salesManagerRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, Constant.REMOVE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }
}
