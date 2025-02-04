package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Sales;
import com.showroommanagement_jwt.service.SalesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PreAuthorize("hasAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseDTO createSales(@RequestBody final Sales sales) {
        return this.salesService.createSales(sales);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return this.salesService.retrieveById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN')")
    @GetMapping("retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.salesService.retrieveAll();
    }

    @PreAuthorize("hasAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("/retrieve-byShowroom-BikeName")
    public ResponseDTO retrieveSalesByShowroomAndBikeName(@RequestParam final String showroomName, @RequestParam final String bikeName) {
        return this.salesService.retrieveSalesByShowroomAndBikeName(showroomName, bikeName);
    }

    @PreAuthorize("hasAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") String id, @RequestBody final Sales sales) {
        return (this.salesService.updateById(id, sales));
    }

    @PreAuthorize("hasAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return this.salesService.deleteById(id);
    }
}
