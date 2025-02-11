package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Showroom;
import com.showroommanagement_jwt.service.ShowroomService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/showroom")
public class ShowroomController {
    private final ShowroomService showroomService;

    public ShowroomController(ShowroomService showroomService) {
        this.showroomService = showroomService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseDTO createShowroom(@RequestBody final Showroom showroom) {
        return this.showroomService.createShowroom(showroom);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable final String id) {
        return this.showroomService.retrieveById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.showroomService.retrieveAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final String id, @RequestBody final Showroom showroom) {
        return this.showroomService.updateById(showroom, id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return this.showroomService.deleteById(id);
    }
}
