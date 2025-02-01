package com.showroommanagement_jwt.dto;

import java.util.Date;

public class SaleDetailDTO {
    private String showroomName;
    private String showroomBrand;
    private String bikeName;
    private Float bikePrice;
    private String salesmanName;
    private String salesManagerName;
    private String customerName;
    private String customerEmail;
    private Long customerContactNumber;
    private Date salesDate;


    public String getShowroomName() {
        return showroomName;
    }

    public void setShowroomName(String showroomName) {
        this.showroomName = showroomName;
    }

    public String getShowroomBrand() {
        return showroomBrand;
    }

    public void setShowroomBrand(String showroomBrand) {
        this.showroomBrand = showroomBrand;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public Float getBikePrice() {
        return bikePrice;
    }

    public void setBikePrice(Float bikePrice) {
        this.bikePrice = bikePrice;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesManagerName() {
        return salesManagerName;
    }

    public void setSalesManagerName(String salesManagerName) {
        this.salesManagerName = salesManagerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getCustomerContactNumber() {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(Long customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }
}
