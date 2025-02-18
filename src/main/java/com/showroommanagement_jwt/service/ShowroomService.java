package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.entity.Showroom;
import com.showroommanagement_jwt.exception.UserNameNotFoundException;
import com.showroommanagement_jwt.repository.ShowroomRepository;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowroomService {
    private final ShowroomRepository showroomRepository;

    public ShowroomService(ShowroomRepository showroomRepository) {
        this.showroomRepository = showroomRepository;
    }

    public Showroom createShowroom(final Showroom showroom) {
        return this.showroomRepository.save(showroom);
    }

    public Showroom retrieveById(final String id) {
        return this.showroomRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));

    }

    public List<Showroom> retrieveAll() {
        return this.showroomRepository.findAll();
    }

    public Showroom updateById(final Showroom showroom, final String id) {
        final Showroom showroomObject = this.showroomRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
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
        return this.showroomRepository.save(showroomObject);
    }

    public String deleteById(final String id) {
        final Showroom showroom = this.showroomRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException("Showroom not found for this id : " + id));
        this.showroomRepository.delete(showroom);
        return Constant.REMOVE;
    }
}
