package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Sale;
import com.showroommanagement_jwt.service.SaleService;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseDTO createSales(@RequestBody final Sale sale) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.saleService.createSales(sale));
    }

    @GetMapping("/retrieve-id/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.saleService.retrieveById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("retrieve-all")
    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.saleService.retrieveAll());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("/retrieve-byShowroom-BikeName")
    public ResponseDTO retrieveSalesByShowroomAndBikeName(@RequestParam final String showroomName, @RequestParam final String bikeName) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.saleService.retrieveSalesByShowroomAndBikeName(showroomName, bikeName));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseDTO updateById(@PathVariable("id") String id, @RequestBody final Sale sale) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.saleService.updateById(id, sale));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseDTO deleteById(@PathVariable("id") final String id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, this.saleService.deleteById(id));
    }
}
