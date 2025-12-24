package com.healthcare.medical_devices_inventory.controller;

import java.util.List;

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

import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;
import com.healthcare.medical_devices_inventory.service.DeviceManufacturerService;

@RestController
@RequestMapping("/api/device-manufacturers")
public class DeviceManufacturerController {

    private final DeviceManufacturerService deviceManufacturerService;

    public DeviceManufacturerController(DeviceManufacturerService deviceManufacturerService) {
        this.deviceManufacturerService = deviceManufacturerService;
    }

    // GET ALL + FILTER
    @GetMapping
    public ResponseEntity<List<DeviceManufacturer>> getManufacturers(
            @RequestParam(required = false) String name) {

        List<DeviceManufacturer> manufacturers =
                deviceManufacturerService.getFilteredManufacturers(name);

        return ResponseEntity.ok(manufacturers);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceManufacturer> getManufacturerById(@PathVariable Long id) {
        return deviceManufacturerService.getManufacturerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<DeviceManufacturer> createManufacturer(
            @RequestBody DeviceManufacturer deviceManufacturer) {

        DeviceManufacturer created =
                deviceManufacturerService.createManufacturer(deviceManufacturer);

        return ResponseEntity.status(201).body(created);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DeviceManufacturer> updateManufacturer(
            @PathVariable Long id,
            @RequestBody DeviceManufacturer deviceManufacturer) {

        DeviceManufacturer updated =
                deviceManufacturerService.updateManufacturer(id, deviceManufacturer);

        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        boolean deleted = deviceManufacturerService.deleteManufacturer(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
