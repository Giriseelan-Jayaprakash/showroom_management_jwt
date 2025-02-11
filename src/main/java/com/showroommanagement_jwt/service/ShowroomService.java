package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Showroom;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.repository.ShowroomRepository;
import com.showroommanagement_jwt.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ShowroomService {
    private final ShowroomRepository showroomRepository;

    public ShowroomService(ShowroomRepository showroomRepository) {
        this.showroomRepository = showroomRepository;
    }

    @Transactional
    public ResponseDTO createShowroom(final Showroom showroom) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.showroomRepository.save(showroom));
    }

    public ResponseDTO retrieveById(final String id) {
        if (this.showroomRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.showroomRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.showroomRepository.findAll());
    }

    @Transactional
    public ResponseDTO updateById(final Showroom showroom, final String id) {
        final Showroom showroomObject = this.showroomRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (showroom.getName() != null) {
            showroomObject.setName(showroom.getName());
        }
        if (showroom.getBrand() != null) {
            showroomObject.setBrand(showroom.getBrand());
        }
        if (showroom.getAddress() != null) {
            showroomObject.setAddress(showroom.getAddress());
        }
        if (showroom.getContactNumber() != 0) {
            showroomObject.setContactNumber(showroom.getContactNumber());
        }
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.showroomRepository.save(showroomObject));
    }

    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.showroomRepository.existsById(id)) {
            this.showroomRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, Constant.REMOVE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }
}
