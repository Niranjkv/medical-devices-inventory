package com.healthcare.medical_devices_inventory.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
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

import com.healthcare.medical_devices_inventory.model.DeviceStatus;
import com.healthcare.medical_devices_inventory.model.DeviceStatusRecord;
import com.healthcare.medical_devices_inventory.service.DeviceStatusRecordService;

@RestController
@RequestMapping("/api/device-status")
public class DeviceStatusRecordController {

    private final DeviceStatusRecordService deviceStatusRecordService;

    public DeviceStatusRecordController(DeviceStatusRecordService deviceStatusRecordService) {
        this.deviceStatusRecordService = deviceStatusRecordService;
    }

    // Create a new DeviceStatusRecord
    @PostMapping("/{deviceId}/status-record")
    public ResponseEntity<DeviceStatusRecord> createDeviceStatusRecord(
            @PathVariable Long deviceId, @RequestBody DeviceStatus status) {

        DeviceStatusRecord statusRecord = deviceStatusRecordService.createDeviceStatusRecord(deviceId, status);
        return new ResponseEntity<>(statusRecord, HttpStatus.CREATED);
    }

    // Get all DeviceStatusRecords for a device
    @GetMapping("/{deviceId}/status-records")
    public Page<DeviceStatusRecord> getDeviceStatusRecords(
            @PathVariable Long deviceId,
            @RequestParam(defaultValue="0") int page, 
            @RequestParam(defaultValue="5") int size) {
        return deviceStatusRecordService.getDeviceStatusRecordsByDeviceId(deviceId, page, size);
    }

    // Get the latest DeviceStatusRecord for a device
    @GetMapping("/{deviceId}/status-records/latest")
    public ResponseEntity<DeviceStatusRecord> getLatestDeviceStatus(@PathVariable Long deviceId) {
        DeviceStatusRecord latestRecord = deviceStatusRecordService.getLatestDeviceStatus(deviceId);
        return new ResponseEntity<>(latestRecord, HttpStatus.OK);
    }

    // Update specific DeviceStatusRecord
    @PutMapping("/status-records/{recordId}")
    public ResponseEntity<DeviceStatusRecord> updateDeviceStatusRecord(
            @PathVariable Long recordId, @RequestBody DeviceStatusRecord updatedRecord) {

        DeviceStatusRecord statusRecord = deviceStatusRecordService.updateDeviceStatusRecord(recordId, updatedRecord);
        return new ResponseEntity<>(statusRecord, HttpStatus.OK);
    }

    // Delete specific DeviceStatusRecord
    @DeleteMapping("/status-records/{recordId}")
    public ResponseEntity<Void> deleteDeviceStatusRecord(@PathVariable Long recordId) {
        deviceStatusRecordService.deleteDeviceStatusRecord(recordId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Search for DeviceStatusRecords by status and date range
    @GetMapping("/status-records/search")
    public ResponseEntity<List<DeviceStatusRecord>> searchDeviceStatusRecords(
            @RequestParam DeviceStatus status,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<DeviceStatusRecord> records = deviceStatusRecordService.searchDeviceStatusRecords(status, startDate, endDate);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
}
