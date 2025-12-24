package com.healthcare.medical_devices_inventory.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String modelNumber;
    private String serialNumber;
    private String description;
    private Integer quantity;
    private LocalDateTime registrationDate;
    private LocalDateTime maintanenceDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private DeviceCategory category; // Many-to-One relationship with DeviceCategory

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private DeviceManufacturer manufacturer; // Many-to-One relationship with DeviceManufacturer

    @ManyToOne
    @JoinColumn(name = "location_id")
    private DeviceLocation location; // Many-to-One relationship with DeviceLocation

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    // @JsonProperty("categoryId") 
    // public Long getCategoryId() {
    //     return category != null ? category.getId() : null;
    // }
}

