package com.healthcare.medical_devices_inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.service.DeviceLocationService;

@RestController
@RequestMapping("/api/device-locations")
public class DeviceLocationController {

    private final DeviceLocationService deviceLocationService;

    public DeviceLocationController(DeviceLocationService deviceLocationService) {
        this.deviceLocationService = deviceLocationService;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<DeviceLocation>> getAllLocations() {
        return ResponseEntity.ok(deviceLocationService.getAllLocations());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceLocation> getLocationById(@PathVariable Long id) {
        DeviceLocation location = deviceLocationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<DeviceLocation> createLocation(
            @RequestBody DeviceLocation deviceLocation) {

        DeviceLocation created = deviceLocationService.createLocation(deviceLocation);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DeviceLocation> updateLocation(
            @PathVariable Long id,
            @RequestBody DeviceLocation deviceLocation) {

        DeviceLocation updated = deviceLocationService.updateLocation(id, deviceLocation);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DeviceLocation>> getLocations(
            @RequestParam(required = false) String locationType) {

        List<DeviceLocation> locations = deviceLocationService.getFilteredLocations(locationType);
        return ResponseEntity.ok(locations);
    }
    
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        deviceLocationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
