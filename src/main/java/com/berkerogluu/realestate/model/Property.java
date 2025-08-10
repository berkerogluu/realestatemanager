package com.berkerogluu.realestate.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.math.BigDecimal;

import com.berkerogluu.realestate.enum.PropertyType;
import com.berkerogluu.realestate.enum.PropertyStatus;
import com.berkerogluu.realestate.enum.HeatingType;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_type", nullable = false);
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(name = "property_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "address_city", nullable = false, length = 255)
    private String addressCity;

    @Column(name = "address_county", nullable = false, length = 255)
    private String addressCounty;

    @Column(name = "address_district", nullable = false, length = 255)
    private String addressDistrict;

    @Column(name = "address_full", nullable = false, length = 500)
    private String addressFull;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "building_floors", nullable = false)
    private Integer buildingFloors;

    @Column(name = "area_net_squaremeter", nullable = false)
    private Integer areaNetSquaremeter;

    @Column(name = "number_of_rooms", nullable = false)
    private Integer numberOfRooms;

    @Column(name = "number_of_halls", nullable = false)
    private Integer numberOfHalls;

    @Column(name = "heating_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HeatingType heatingType;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Property {

    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    // Custom methods
    // getPriceDisplay(): Shows price monthly or one-time based on what PropertyType it is
    public String getPriceDisplay() {
        if(propertyType == PropertyType.RENTAL) {
            return price + "TRY" + "/ay";
        }else if(propertyType == PropertyType == ONSALE) {
            return price + "TRY";
        }
        return price + "TRY";
    }

    // Getters
    public Long getId() {
        return id;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public PropertyStatus getPropertyStatus() {
        return PropertyStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAdressCity() {
        return addressCity;
    }

    public String getAddressCounty() {
        return addressCounty;
    }

    public String getAdressDistrict() {
        return addressDistrict;
    }

    public String getAddressFull() {
        return addressFull;
    }

    public Integer getFloor() {
        return floor;
    }

    public Integer getBuildingFloors() {
        return buildingFloors;
    }

    public Integer getAreaNetSquaremeter() {
        return areaNetSquaremeter;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Integer getNumberOfHalls() {
        return numberOfHalls;
    }

    public HeatingType getHeatingType() {
        return heatingType;
    }

    public BigDecimal getPrice() {
        return price;
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

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public void setPropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public void setAddressFull(String addressFull) {
        this.addressFull = addressFull;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setBuildingFloors(Integer buildingFloors) {
        this.buildingFloors = buildingFloors;
    }

    public void setAreaNetSquaremeter(Integer areaNetSquaremeter) {
        this.areaNetSquaremeter = areaNetSquaremeter;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setNumberOfHalls(Integer numberOfHalls) {
        this.numberOfHalls = numberOfHalls;
    }

    public void setHeatingType(HeatingType heatingType) {
        this.heatingType = heatingType;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


}
