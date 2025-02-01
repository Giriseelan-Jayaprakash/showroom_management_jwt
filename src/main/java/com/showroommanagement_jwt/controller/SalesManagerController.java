package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.SalesManager;
import com.showroommanagement_jwt.service.SalesManagerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales-manager")
public class SalesManagerController {
    private final SalesManagerService salesManagerService;

    public SalesManagerController(SalesManagerService salesManagerService) {
        this.salesManagerService = salesManagerService;
    }

    @PostMapping("/create")
    public ResponseDTO createSalesManager(@RequestBody final SalesManager salesManager) {
        return this.salesManagerService.createSalesManager(salesManager);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final Integer id) {
        return this.salesManagerService.retrieveById(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveALl() {
        return this.salesManagerService.retrieveALl();
    }

    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final Integer id, @RequestBody final SalesManager salesManager) {
        return this.salesManagerService.updateById(id, salesManager);
    }

    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final Integer id) {
        return this.salesManagerService.deleteById(id);
    }
}
