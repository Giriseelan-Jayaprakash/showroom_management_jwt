package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Sales;
import com.showroommanagement_jwt.service.SalesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping("/create")
    public ResponseDTO createSales(@RequestBody final Sales sales) {
        return this.salesService.createSales(sales);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final Integer id) {
        return this.salesService.retrieveById(id);
    }

    @GetMapping("retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.salesService.retrieveAll();
    }

    @GetMapping("/retrieve-byShowroom-BikeName")
    public ResponseDTO retrieveSalesByShowroomAndBikeName(@RequestParam final String showroomName, @RequestParam final String bikeName) {
        return this.salesService.retrieveSalesByShowroomAndBikeName(showroomName, bikeName);
    }

    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") Integer id, @RequestBody final Sales sales) {
        return (this.salesService.updateById(id, sales));
    }

    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final Integer id) {
        return this.salesService.deleteById(id);
    }
}
