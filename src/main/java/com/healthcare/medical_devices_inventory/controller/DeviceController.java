package com.healthcare.medical_devices_inventory.controller;

import java.util.List;
import java.util.Optional;

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

import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.service.DeviceService;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;
    
    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices(){
        List<Device> devices = deviceService.getAllDevices();
        return new ResponseEntity<>(devices,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") Long deviceId){
        Optional<Device> deviceOpt = deviceService.getDeviceById(deviceId);
        if(deviceOpt.isPresent()){
            Device device = deviceOpt.get();
            return new ResponseEntity<>(device,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Device> createDevice(@RequestBody Device device){
        Device createdDevice = deviceService.createDevice(device);
        return new ResponseEntity<>(createdDevice,HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") Long deviceId,@RequestBody Device device){
        Optional<Device> updatedDeviceOpt = deviceService.update(deviceId, device);
        if(updatedDeviceOpt.isPresent()){
            Device updatedDevice = updatedDeviceOpt.get();
            return new ResponseEntity<>(updatedDevice,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteDevice(@PathVariable("id") Long deviceId){
        boolean deleted = deviceService.deleteDevice(deviceId);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
