package com.healthcare.medical_devices_inventory.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String modelNumber;
    private String serialNumber;
    private String description;
    private Integer quantity;
    private String registrationDate;
    private String maintanenceDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
