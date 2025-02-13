package com.showroommanagement_jwt.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "sales_date")
    private Date salesDate;
    @Column(name = "sales_price")
    private double salesPrice;

    public Sales() {
    }

    public Sales(String id, Date salesDate, double salesPrice, Customer customer, Bike bike) {
        this.id = id;
        this.salesDate = salesDate;
        this.salesPrice = salesPrice;
        this.customer = customer;
        this.bike = bike;
    }

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name = "bike_id")
    private Bike bike;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
