package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Bike;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.repository.BikeRepository;
import com.showroommanagement_jwt.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Transactional
    public ResponseDTO createBike(final Bike bike) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.bikeRepository.save(bike));
    }

    public ResponseDTO retrieveById(final String id) {
        if (this.bikeRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.bikeRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.bikeRepository.findAll());
    }

    @Transactional
    public ResponseDTO updateById(final String id, final Bike bike) {
        final Bike bikeObject = this.bikeRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (bike.getName() != null) {
            bikeObject.setName(bike.getName());
        }
        if (bike.getCc() != 0) {
            bikeObject.setCc(bike.getCc());
        }
        if (bike.getMileage() != 0) {
            bikeObject.setMileage(bike.getMileage());
        }
        if (bike.getPrice() != 0) {
            bikeObject.setPrice(bike.getPrice());
        }
        if (bike.getStock() != null) {
            bikeObject.setStock(bike.getStock());
        }
        if (bike.getSalesman() != null) {
            bikeObject.setSalesman(bike.getSalesman());
        }
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, bikeRepository.save(bikeObject));
    }

    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.bikeRepository.existsById(id)) {
            this.bikeRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, Constant.REMOVE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }


    }
}

