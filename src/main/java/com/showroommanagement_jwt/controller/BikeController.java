package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Bike;
import com.showroommanagement_jwt.service.BikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bike")
public class BikeController {
    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @PostMapping("/create")
    public ResponseDTO createBike(@RequestBody final Bike bike) {
        return this.bikeService.createBike(bike);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final Integer id) {
        return this.bikeService.retrieveById(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.bikeService.retrieveAll();
    }

    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final Integer id, @RequestBody Bike bike) {
        return this.bikeService.updateById(id, bike);
    }

    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final Integer id) {
        return this.bikeService.deleteById(id);
    }
}
