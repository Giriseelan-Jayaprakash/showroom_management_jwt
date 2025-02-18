package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Bike;
import com.showroommanagement_jwt.service.BikeService;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bike")
public class BikeController {
    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseDTO createBike(@RequestBody final Bike bike) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.bikeService.createBike(bike));
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.bikeService.retrieveById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN','ROLE_CUSTOMER')")
    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.bikeService.retrieveAll());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final String id, @RequestBody Bike bike) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.bikeService.updateById(id, bike));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, this.bikeService.deleteById(id));
    }
}
