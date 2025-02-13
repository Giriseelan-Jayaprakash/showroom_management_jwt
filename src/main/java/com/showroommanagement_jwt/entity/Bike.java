package com.showroommanagement_jwt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bike")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "cc")
    private float cc;
    @Column(name = "mileage")
    private float mileage;
    @Column(name = "price")
    private float price;
    @Column(name = "stock")
    private String stock;

    public Bike() {
    }

    public Bike(String id, String name, float cc, float mileage, float price, String stock, Salesman salesman) {
        this.id = id;
        this.name = name;
        this.cc = cc;
        this.mileage = mileage;
        this.price = price;
        this.stock = stock;
        this.salesman = salesman;
    }

    @ManyToOne()
    @JoinColumn(name = "salesman_id")
    private Salesman salesman;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCc() {
        return cc;
    }

    public void setCc(float cc) {
        this.cc = cc;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

}
