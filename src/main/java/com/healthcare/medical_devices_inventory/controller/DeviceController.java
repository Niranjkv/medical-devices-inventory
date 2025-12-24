package com.healthcare.medical_devices_inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;
import com.healthcare.medical_devices_inventory.service.DeviceService;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO dto) {
        Device device = mapToEntity(dto);
        Device savedDevice = deviceService.createDevice(device);
        return new ResponseEntity<>(mapToDTO(savedDevice), HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/{deviceId}/update")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long deviceId, @RequestBody DeviceDTO dto) {
        Device device = mapToEntity(dto);
        Device updatedDevice = deviceService.updateDevice(deviceId, device);
        return ResponseEntity.ok(mapToDTO(updatedDevice));
    }

    // GET BY ID
    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        return ResponseEntity.ok(mapToDTO(device));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> list = deviceService.getAllDevices()
                .stream()
                .map(this::mapToDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    // DELETE
    @DeleteMapping("/{deviceId}/delete")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DeviceDTO>> getDevices(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) Long manufacturerId) {
        List<Device> devices = deviceService.getFilteredDevices(categoryId, locationId, manufacturerId);
        return ResponseEntity.ok(devices.stream().map(this::mapToDTO).toList());
    }

    /* ================= MAPPERS ================= */

    private Device mapToEntity(DeviceDTO dto) {
        Device device = new Device();
        device.setName(dto.getName());
        device.setModelNumber(dto.getModelNumber());
        device.setSerialNumber(dto.getSerialNumber());
        device.setDescription(dto.getDescription());
        device.setQuantity(dto.getQuantity());
        device.setRegistrationDate(dto.getRegistrationDate());
        device.setMaintanenceDate(dto.getMaintanenceDate());

        if (dto.getCategoryId() != null) {
            DeviceCategory category = new DeviceCategory();
            category.setId(dto.getCategoryId());
            device.setCategory(category);
        }

        if (dto.getManufacturerId() != null) {
            DeviceManufacturer manufacturer = new DeviceManufacturer();
            manufacturer.setId(dto.getManufacturerId());
            device.setManufacturer(manufacturer);
        }

        if (dto.getLocationId() != null) {
            DeviceLocation location = new DeviceLocation();
            location.setId(dto.getLocationId());
            device.setLocation(location);
        }

        return device;
    }

    private DeviceDTO mapToDTO(Device device) {
        DeviceDTO dto = new DeviceDTO();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setModelNumber(device.getModelNumber());
        dto.setSerialNumber(device.getSerialNumber());
        dto.setDescription(device.getDescription());
        dto.setQuantity(device.getQuantity());
        dto.setRegistrationDate(device.getRegistrationDate());
        dto.setMaintanenceDate(device.getMaintanenceDate());

        if (device.getCategory() != null) {
            dto.setCategoryId(device.getCategory().getId());
        }

        if (device.getManufacturer() != null) {
            dto.setManufacturerId(device.getManufacturer().getId());
        }

        if (device.getLocation() != null) {
            dto.setLocationId(device.getLocation().getId());
        }

        return dto;
    }
}
