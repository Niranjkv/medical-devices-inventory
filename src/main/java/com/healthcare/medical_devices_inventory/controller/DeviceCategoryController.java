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

import com.healthcare.medical_devices_inventory.dto.DeviceCategoryDTO;
import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.service.DeviceCategoryService;

@RestController
@RequestMapping("/api/device-categories")
public class DeviceCategoryController {

    private final DeviceCategoryService service;

    public DeviceCategoryController(DeviceCategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceCategoryDTO> create(@RequestBody DeviceCategory category) {
        return new ResponseEntity<>(service.create(category), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DeviceCategoryDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceCategoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceCategoryDTO> update(
            @PathVariable Long id,
            @RequestBody DeviceCategory category) {

        return ResponseEntity.ok(service.update(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<List<DeviceDTO>> getDevicesByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDevicesByCategory(id));
    }
}
