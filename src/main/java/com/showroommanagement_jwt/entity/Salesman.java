package com.showroommanagement_jwt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salesman")
public class Salesman {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "salary")
    private double salary;
    @Column(name = "experience")
    private float experience;
    @Column(name = "address")
    private String address;
    @Column(name = "contact_number")
    private float contactNumber;

    public Salesman() {
    }

    public Salesman(String id, String name, double salary, float experience, String address, float contactNumber, SalesManager salesManager) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.experience = experience;
        this.address = address;
        this.contactNumber = contactNumber;
        this.salesManager = salesManager;
    }

    @ManyToOne()
    @JoinColumn(name = "sales_manager_id")
    private SalesManager salesManager;

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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(float contactNumber) {
        this.contactNumber = contactNumber;
    }

    public SalesManager getSalesManager() {
        return salesManager;
    }

    public void setSalesManager(SalesManager salesManager) {
        this.salesManager = salesManager;
    }
}
