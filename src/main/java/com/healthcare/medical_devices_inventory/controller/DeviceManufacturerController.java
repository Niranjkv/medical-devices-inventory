package com.healthcare.medical_devices_inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;
import com.healthcare.medical_devices_inventory.service.DeviceManufacturerService;

@RestController
@RequestMapping("/api/device-manufacturers")
public class DeviceManufacturerController {

    private final DeviceManufacturerService deviceManufacturerService;

    public DeviceManufacturerController(DeviceManufacturerService deviceManufacturerService) {
        this.deviceManufacturerService = deviceManufacturerService;
    }

    // Get all device manufacturers
    @GetMapping
    public List<DeviceManufacturer> getAllManufacturers() {
        return deviceManufacturerService.getAllManufacturers();
    }

    // Get device manufacturer by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceManufacturer> getManufacturerById(@PathVariable Long id) {
        Optional<DeviceManufacturer> manufacturer = deviceManufacturerService.getManufacturerById(id);
        return manufacturer.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new device manufacturer
    @PostMapping
    public ResponseEntity<DeviceManufacturer> createManufacturer(@RequestBody DeviceManufacturer deviceManufacturer) {
        DeviceManufacturer createdManufacturer = deviceManufacturerService.createManufacturer(deviceManufacturer);
        return ResponseEntity.status(201).body(createdManufacturer);
    }

    // Update an existing device manufacturer
    @PutMapping("/{id}")
    public ResponseEntity<DeviceManufacturer> updateManufacturer(@PathVariable Long id, 
                                                                @RequestBody DeviceManufacturer deviceManufacturer) {
        DeviceManufacturer updatedManufacturer = deviceManufacturerService.updateManufacturer(id, deviceManufacturer);
        return updatedManufacturer != null ? ResponseEntity.ok(updatedManufacturer) 
                                           : ResponseEntity.notFound().build();
    }

    // Delete a device manufacturer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        boolean deleted = deviceManufacturerService.deleteManufacturer(id);
        return deleted ? ResponseEntity.noContent().build() 
                       : ResponseEntity.notFound().build();
    }
}
