package com.showroommanagement_jwt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salesman")
public class Salesman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
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

    @ManyToOne()
    @JoinColumn(name = "showroom_id")
    private Showroom showroom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }
}
