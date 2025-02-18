package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.entity.Bike;
import com.showroommanagement_jwt.exception.UserNameNotFoundException;
import com.showroommanagement_jwt.repository.BikeRepository;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public Bike createBike(final Bike bike) {
        return this.bikeRepository.save(bike);
    }

    public Bike retrieveById(final String id) {
        return this.bikeRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Bike> retrieveAll() {
        return this.bikeRepository.findAll();
    }

    public Bike updateById(final String id, final Bike bike) {
        final Bike bikeObject = this.bikeRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
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
        return this.bikeRepository.save(bikeObject);
    }

    public String deleteById(final String id) {
        final Bike bike = this.bikeRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException("Bike not found for this id : " + id));
        this.bikeRepository.delete(bike);
        return Constant.REMOVE;
    }
}

