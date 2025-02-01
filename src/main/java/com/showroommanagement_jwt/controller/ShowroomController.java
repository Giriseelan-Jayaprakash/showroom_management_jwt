package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Showroom;
import com.showroommanagement_jwt.service.ShowroomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/showroom")
public class ShowroomController {
    private final ShowroomService showroomService;

    public ShowroomController(ShowroomService showroomService) {
        this.showroomService = showroomService;
    }

    @PostMapping("/create")
    public ResponseDTO createShowroom(@RequestBody final Showroom showroom) {
        return this.showroomService.createShowroom(showroom);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable final Integer id) {
        return this.showroomService.retrieveById(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.showroomService.retrieveAll();
    }

    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final Integer id, @RequestBody final Showroom showroom) {
        return this.showroomService.updateById(showroom, id);
    }

    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final Integer id) {
        return this.showroomService.deleteById(id);
    }
}
