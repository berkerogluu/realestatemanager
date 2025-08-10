package com.berkerogluu.realestate.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName;

    @Column(name = "customer_lastname", nullable = false, length = 255)
    private String customerLastname;

    @Column(name = "mobile_phone", nullable = false, length = 16)
    private String mobilePhone;

    @Column(name = "home_number", nullable = false, length = 16)
    private String homeNumber;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    // Customer can be a buyer, seller, landlord and tenant at the same time (design choice)
    @Column(name = "is_buyer")
    private Boolean isBuyer = false;

    @Column(name = "is_seller")
    private Boolean isSeller = false;

    @Column(name = "is_landlord")
    private Boolean isLandlord = false;

    @Column(name = "is_tenant")
    private Boolean isTenant = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Customer() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getIsBuyer() {
        return isBuyer;
    }

    public Boolean getIsSeller() {
        return isSeller;
    }

    public Boolean getIsLandlord() {
        return isLandlord;
    }

    public Boolean getIsTenant() {
        return isTenant;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone= = mobilePhone;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIsBuyer(Boolean isBuyer) {
        this.isBuyer = isBuyer;
    }

    public void setIsSeller(Boolean isSeller) {
        this.isSeller = isSeller;
    }

    public void setIsLandlord(Boolean isLandlord) {
        this.isLandlord = isLandlord;
    }

    public void setIsTenant(Boolean isTenant) {
       this.isTenant = isTenant;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
