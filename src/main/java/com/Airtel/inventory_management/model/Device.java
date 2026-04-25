package com.Airtel.inventory_management.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String serialNumber;
    
    private String deviceType;
    private String make;
    private String model;
    private String specifications;
    
    @Column(name = "device_condition")
    private String deviceCondition;
    
    private String status;
    private String purchaseDate;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Device() {
        this.deviceCondition = "Good";
        this.status = "Available";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getSerialNumber() { return serialNumber; }
    public String getDeviceType() { return deviceType; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getSpecifications() { return specifications; }
    public String getDeviceCondition() { return deviceCondition; }
    public String getStatus() { return status; }
    public String getPurchaseDate() { return purchaseDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }
    public void setDeviceCondition(String deviceCondition) { this.deviceCondition = deviceCondition; }
    public void setStatus(String status) { this.status = status; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}