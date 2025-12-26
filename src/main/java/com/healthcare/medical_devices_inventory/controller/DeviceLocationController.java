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
import com.healthcare.medical_devices_inventory.dto.DeviceLocationDTO;
import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.service.DeviceLocationService;

@RestController
@RequestMapping("/api/device-locations")
public class DeviceLocationController {

    private final DeviceLocationService service;

    public DeviceLocationController(DeviceLocationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceLocationDTO> create(
            @RequestBody DeviceLocation location) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceLocationDTO> update(
            @PathVariable Long id,
            @RequestBody DeviceLocation location) {

        return ResponseEntity.ok(service.update(id, location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceLocationDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public List<DeviceLocationDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<List<DeviceDTO>> getDevicesByLocation(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDevicesByLocation(id));
    }
}
