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
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(Constant.CREATE);
        responseDTO.setStatusCode(HttpStatus.CREATED.value());
        responseDTO.setData(this.bikeRepository.save(bike));
        return responseDTO;
    }

    public ResponseDTO retrieveById(final Integer id) {
        if (this.bikeRepository.existsById(id)) {
            final ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage(Constant.RETRIEVE);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setData(this.bikeRepository.findById(id));
            return responseDTO;
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(Constant.RETRIEVE);
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setData(this.bikeRepository.findAll());
        return responseDTO;
    }

    @Transactional
    public ResponseDTO updateById(final Integer id, final Bike bike) {
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
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(Constant.UPDATE);
        responseDTO.setStatusCode(HttpStatus.OK.value());
        responseDTO.setData(bikeRepository.save(bikeObject));
        return responseDTO;
    }

    public ResponseDTO deleteById(final Integer id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.bikeRepository.existsById(id)) {
            this.bikeRepository.deleteById(id);
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

