package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Salesman;
import com.showroommanagement_jwt.service.SalesmanService;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/salesman")
public class SalesmanController {
    private final SalesmanService salesmanService;

    public SalesmanController(SalesmanService salesmanService) {
        this.salesmanService = salesmanService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseDTO createSalesman(@RequestBody final Salesman salesman) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.salesmanService.createSalesman(salesman));
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesmanService.retrieveById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesmanService.retrieveAll());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final String id, final Salesman salesman) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.salesmanService.updateById(id, salesman));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, this.salesmanService.deleteById(id));
    }
}
