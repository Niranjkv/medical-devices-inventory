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
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.service.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/create")
    public ResponseEntity<Device> createDevice(@RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.createDevice(deviceDTO);
        return new ResponseEntity<>(device, HttpStatus.CREATED);
    }

    @PutMapping("/{deviceId}/update")
    public ResponseEntity<Device> updateDevice(@PathVariable Long deviceId, @RequestBody DeviceDTO deviceDTO) {
        Device updatedDevice = deviceService.updateDevice(deviceId, deviceDTO);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getDevices(){
        List<Device> list = deviceService.getAllDevices();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @DeleteMapping("/{deviceId}/delete")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        deviceService.deleteDevice(deviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
