package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Salesman;
import com.showroommanagement_jwt.service.SalesmanService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO> createSalesman(@RequestBody final Salesman salesman) {
        ResponseDTO responseDTO = this.salesmanService.createSalesman(salesman);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN','ROLE_SALESMAN')")
    @GetMapping("/retrieve-id/{id}")
    public ResponseEntity<ResponseDTO> retrieveById(@PathVariable("id") final String id) {
        ResponseDTO responseDTO = this.salesmanService.retrieveById(id);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("/retrieve-all")
    public ResponseEntity<ResponseDTO> retrieveAll() {
        ResponseDTO responseDTO = this.salesmanService.retrieveAll();
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable("id") final String id, final Salesman salesman) {
        ResponseDTO responseDTO = this.salesmanService.updateById(id, salesman);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable("id") final String id) {
        ResponseDTO responseDTO = this.salesmanService.deleteById(id);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
