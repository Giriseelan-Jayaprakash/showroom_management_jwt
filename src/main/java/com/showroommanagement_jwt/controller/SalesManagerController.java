package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.SalesManager;
import com.showroommanagement_jwt.service.SalesManagerService;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales-manager")
public class SalesManagerController {
    private final SalesManagerService salesManagerService;

    public SalesManagerController(SalesManagerService salesManagerService) {
        this.salesManagerService = salesManagerService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseDTO createSalesManager(@RequestBody final SalesManager salesManager) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.salesManagerService.createSalesManager(salesManager));
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesManagerService.retrieveById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveALl() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesManagerService.retrieveALl());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final String id, @RequestBody final SalesManager salesManager) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.salesManagerService.updateById(id, salesManager));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, this.salesManagerService.deleteById(id));
    }
}
