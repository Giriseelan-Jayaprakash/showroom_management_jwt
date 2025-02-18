package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Showroom;
import com.showroommanagement_jwt.service.ShowroomService;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.http.HttpStatus;
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
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.showroomService.createShowroom(showroom));
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.showroomService.retrieveById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.showroomService.retrieveAll());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final String id, @RequestBody final Showroom showroom) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.showroomService.updateById(showroom, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, this.showroomService.deleteById(id));
    }
}
