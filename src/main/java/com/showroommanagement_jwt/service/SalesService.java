package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.dto.SaleDetailDTO;
import com.showroommanagement_jwt.entity.Sales;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.repository.SalesRepository;
import com.showroommanagement_jwt.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {
    private final SalesRepository salesRepository;

    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Transactional
    public ResponseDTO createSales(final Sales sales) {
        return new ResponseDTO(HttpStatus.CREATED.value(), Constant.CREATE, this.salesRepository.save(sales));
    }

    public ResponseDTO retrieveById(final String id) {
        if (this.salesRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }

    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.salesRepository.findAll());
    }

    public ResponseDTO retrieveSalesByShowroomAndBikeName(final String showroomName, final String bikeName) {
        List<Sales> sales = this.salesRepository.retrieveSalesByShowroomAndBikeName(showroomName, bikeName);
        List<SaleDetailDTO> saleDetailDTOList = new ArrayList<>();
        for (Sales sales1 : sales) {
            SaleDetailDTO saleDetailDTO = new SaleDetailDTO();
            saleDetailDTO.setShowroomName(sales1.getBike().getSalesman().getSalesManager().getShowroom().getName());
            saleDetailDTO.setShowroomBrand(sales1.getBike().getSalesman().getSalesManager().getShowroom().getBrand());
            saleDetailDTO.setSalesManagerName(sales1.getBike().getSalesman().getSalesManager().getName());
            saleDetailDTO.setSalesmanName(sales1.getCustomer().getSalesman().getName());
            saleDetailDTO.setBikeName(sales1.getBike().getName());
            saleDetailDTO.setBikePrice(sales1.getBike().getPrice());
            saleDetailDTO.setCustomerName(sales1.getCustomer().getName());
            saleDetailDTO.setCustomerEmail(sales1.getCustomer().getEmail());
            saleDetailDTO.setCustomerContactNumber(sales1.getCustomer().getContactNumber());
            saleDetailDTO.setSalesDate(sales1.getSalesDate());
            saleDetailDTOList.add(saleDetailDTO);
        }
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, saleDetailDTOList);
    }

    @Transactional
    public ResponseDTO updateById(final String id, final Sales sales) {
        final Sales salesObject = this.salesRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (sales.getSalesDate() != null) {
            salesObject.setSalesDate(sales.getSalesDate());
        }
        if (sales.getSalesPrice() != 0) {
            salesObject.setSalesPrice(sales.getSalesPrice());
        }
        if (sales.getCustomer() != null) {
            salesObject.setCustomer(sales.getCustomer());
        }
        if (sales.getBike() != null) {
            salesObject.setBike(sales.getBike());
        }
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.salesRepository.save(salesObject));
    }

    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.salesRepository.existsById(id)) {
            this.salesRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, Constant.REMOVE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }
}
