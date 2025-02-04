package com.showroommanagement_jwt.controller;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.SalesManager;
import com.showroommanagement_jwt.service.SalesManagerService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO> createSalesManager(@RequestBody final SalesManager salesManager) {
        ResponseDTO responseDTO = this.salesManagerService.createSalesManager(salesManager);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("/retrieve-id/{id}")
    public ResponseEntity<ResponseDTO> retrieveById(@PathVariable("id") final String id) {
        ResponseDTO responseDTO = this.salesManagerService.retrieveById(id);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @GetMapping("/retrieve-all")
    public ResponseEntity<ResponseDTO> retrieveALl() {
        ResponseDTO responseDTO = this.salesManagerService.retrieveALl();
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @PutMapping("/update-id/{id}")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable("id") final String id, @RequestBody final SalesManager salesManager) {
        ResponseDTO responseDTO = this.salesManagerService.updateById(id, salesManager);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SALES_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/delete-id/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable("id") final String id) {
        ResponseDTO responseDTO = this.salesManagerService.deleteById(id);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
