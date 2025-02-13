package com.showroommanagement_jwt.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "showroom")
public class Showroom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "brand")
    private String brand;
    @Column(name = "address")
    private String address;
    @Column(name = "contact_number")
    private long contactNumber;

    public Showroom() {
    }

    public Showroom(String id, String name, String brand, String address, long contactNumber) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
