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
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.dto.DeviceManufacturerDTO;
import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;
import com.healthcare.medical_devices_inventory.service.DeviceManufacturerService;

@RestController
@RequestMapping("/api/device-manufacturers")
public class DeviceManufacturerController {

    private final DeviceManufacturerService service;

    public DeviceManufacturerController(DeviceManufacturerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceManufacturerDTO> create(
            @RequestBody DeviceManufacturer manufacturer) {

        return new ResponseEntity<>(service.create(manufacturer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DeviceManufacturerDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceManufacturerDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceManufacturerDTO> update(
            @PathVariable Long id,
            @RequestBody DeviceManufacturer manufacturer) {

        return ResponseEntity.ok(service.update(id, manufacturer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<List<DeviceDTO>> getDevicesByManufacturer(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDevicesByManufacturer(id));
    }
}
