package com.healthcare.medical_devices_inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.service.DeviceLocationService;

@RestController
@RequestMapping("/api/device-locations")
public class DeviceLocationController {

    private final DeviceLocationService deviceLocationService;

    @Autowired
    public DeviceLocationController(DeviceLocationService deviceLocationService) {
        this.deviceLocationService = deviceLocationService;
    }

    // Get all device locations
    @GetMapping
    public List<DeviceLocation> getAllLocations() {
        return deviceLocationService.getAllLocations();
    }

    // Get device location by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceLocation> getLocationById(@PathVariable Long id) {
        Optional<DeviceLocation> location = deviceLocationService.getLocationById(id);
        return location.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new device location
    @PostMapping
    public ResponseEntity<DeviceLocation> createLocation(@RequestBody DeviceLocation deviceLocation) {
        DeviceLocation createdLocation = deviceLocationService.createLocation(deviceLocation);
        return ResponseEntity.status(201).body(createdLocation);
    }

    // Update an existing device location
    @PutMapping("/{id}")
    public ResponseEntity<DeviceLocation> updateLocation(@PathVariable Long id, 
                                                         @RequestBody DeviceLocation deviceLocation) {
        DeviceLocation updatedLocation = deviceLocationService.updateLocation(id, deviceLocation);
        return updatedLocation != null ? ResponseEntity.ok(updatedLocation) 
                                       : ResponseEntity.notFound().build();
    }

    // Delete a device location
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        boolean deleted = deviceLocationService.deleteLocation(id);
        return deleted ? ResponseEntity.noContent().build() 
                       : ResponseEntity.notFound().build();
    }
}
