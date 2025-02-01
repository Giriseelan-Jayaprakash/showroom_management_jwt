package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Salesman;
import com.showroommanagement_jwt.service.SalesmanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/salesman")
public class SalesmanController {
    private final SalesmanService salesmanService;

    public SalesmanController(SalesmanService salesmanService) {
        this.salesmanService = salesmanService;
    }

    @PostMapping("/create")
    public ResponseDTO createSalesman(@RequestBody final Salesman salesman) {
        return this.salesmanService.createSalesman(salesman);
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final Integer id) {
        return this.salesmanService.retrieveById(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.salesmanService.retrieveAll();
    }

    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") final Integer id, final Salesman salesman) {
        return this.salesmanService.updateById(id, salesman);
    }

    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final Integer id) {
        return this.salesmanService.deleteById(id);
    }
}
