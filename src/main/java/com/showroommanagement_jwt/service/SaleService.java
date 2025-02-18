package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.SaleDetailDTO;
import com.showroommanagement_jwt.entity.Sale;
import com.showroommanagement_jwt.exception.UserNameNotFoundException;
import com.showroommanagement_jwt.repository.SaleRepository;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale createSales(final Sale sale) {
        return this.saleRepository.save(sale);
    }

    public Sale retrieveById(final String id) {
        return this.saleRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Sale> retrieveAll() {
        return this.saleRepository.findAll();
    }

    public List<SaleDetailDTO> retrieveSalesByShowroomAndBikeName(final String showroomName, final String bikeName) {
        List<Sale> sale = this.saleRepository.retrieveSaleByShowroomAndBikeName(showroomName, bikeName);
        List<SaleDetailDTO> saleDetailDTOList = new ArrayList<>();
        for (Sale sale1 : sale) {
            SaleDetailDTO saleDetailDTO = new SaleDetailDTO();
            saleDetailDTO.setShowroomName(sale1.getBike().getSalesman().getSalesManager().getShowroom().getName());
            saleDetailDTO.setShowroomBrand(sale1.getBike().getSalesman().getSalesManager().getShowroom().getBrand());
            saleDetailDTO.setSalesManagerName(sale1.getBike().getSalesman().getSalesManager().getName());
            saleDetailDTO.setSalesmanName(sale1.getCustomer().getSalesman().getName());
            saleDetailDTO.setBikeName(sale1.getBike().getName());
            saleDetailDTO.setBikePrice(sale1.getBike().getPrice());
            saleDetailDTO.setCustomerName(sale1.getCustomer().getName());
            saleDetailDTO.setCustomerEmail(sale1.getCustomer().getEmail());
            saleDetailDTO.setCustomerContactNumber(sale1.getCustomer().getContactNumber());
            saleDetailDTO.setSalesDate(sale1.getSalesDate());
            saleDetailDTOList.add(saleDetailDTO);
        }
        return saleDetailDTOList;
    }

    public Sale updateById(final String id, final Sale sale) {
        final Sale saleObject = this.saleRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.ID_DOES_NOT_EXIST));
        if (sale.getSalesDate() != null) {
            saleObject.setSalesDate(sale.getSalesDate());
        }
        if (sale.getSalesPrice() != 0) {
            saleObject.setSalesPrice(sale.getSalesPrice());
        }
        if (sale.getCustomer() != null) {
            saleObject.setCustomer(sale.getCustomer());
        }
        if (sale.getBike() != null) {
            saleObject.setBike(sale.getBike());
        }
        return this.saleRepository.save(saleObject);
    }

    public String deleteById(final String id) {
        final Sale sale = this.saleRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException("Sale not found for this id : " + id));
        this.saleRepository.delete(sale);
        return Constant.REMOVE;
    }
}
