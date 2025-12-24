package com.healthcare.medical_devices_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.model.DeviceManufacturer;
import com.healthcare.medical_devices_inventory.repository.DeviceCategoryRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceLocationRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceManufacturerRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceRepository;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;
    private final DeviceLocationRepository deviceLocationRepository;
    private final DeviceManufacturerRepository deviceManufacturerRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, DeviceCategoryRepository deviceCategoryRepository, DeviceLocationRepository deviceLocationRepository, DeviceManufacturerRepository deviceManufacturerRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceCategoryRepository = deviceCategoryRepository;
        this.deviceLocationRepository = deviceLocationRepository;
        this.deviceManufacturerRepository = deviceManufacturerRepository;
    }

   @Transactional
    public Device createDevice(Device device) {
        if (device.getMaintanenceDate() != null) {
            device.setMaintanenceDate(device.getMaintanenceDate());
        }   
        // Set category
        DeviceCategory category = deviceCategoryRepository.findById(device.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        device.setCategory(category);

        // Set manufacturer
        if (device.getManufacturer() != null && device.getManufacturer().getId() != null) {
            DeviceManufacturer manufacturer = deviceManufacturerRepository.findById(device.getManufacturer().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found"));
            device.setManufacturer(manufacturer);
        }

        // Set location
        if (device.getLocation() != null && device.getLocation().getId() != null) {
            DeviceLocation location = deviceLocationRepository.findById(device.getLocation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
            device.setLocation(location);
        }

        return deviceRepository.save(device);  // Save and return the entity
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();  // No DTO needed here for internal operations
    }

    @Transactional(readOnly = true)
    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId));
    }

    @Transactional
    public Device updateDevice(Long deviceId, Device device) {
        Device existingDevice = getDeviceById(deviceId);
        // Update the fields from the incoming device
        existingDevice.setModelNumber(device.getModelNumber());
        existingDevice.setSerialNumber(device.getSerialNumber());
        existingDevice.setDescription(device.getDescription());
        existingDevice.setQuantity(device.getQuantity());
        existingDevice.setRegistrationDate(device.getRegistrationDate());
         if (device.getMaintanenceDate() != null) {
            existingDevice.setMaintanenceDate(device.getMaintanenceDate());
        }
        // If a new category is provided, update the category
        if (device.getCategory() != null) {
            DeviceCategory category = deviceCategoryRepository.findById(device.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingDevice.setCategory(category);
        }
        // If a new manufacturer is provided, update the manufacturer
        if (device.getManufacturer() != null) {
            DeviceManufacturer manufacturer = deviceManufacturerRepository.findById(device.getManufacturer().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found"));
            existingDevice.setManufacturer(manufacturer);
        }

        // If a new location is provided, update the location
        if (device.getLocation() != null) {
            DeviceLocation location = deviceLocationRepository.findById(device.getLocation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
            existingDevice.setLocation(location);
        }

        
        return deviceRepository.save(existingDevice);
    }

    @Transactional(readOnly = true)
    public List<Device> getFilteredDevices(Long categoryId, Long locationId, Long manufacturerId) {
        if (categoryId != null) {
            return deviceRepository.findByCategoryId(categoryId);
        } else if (locationId != null) {
            return deviceRepository.findByLocationId(locationId);
        } else if (manufacturerId != null) {
            return deviceRepository.findByManufacturerId(manufacturerId);
        }
        return deviceRepository.findAll();  // No filter, return all devices
    }
    
    @Transactional
    public boolean deleteDevice(Long deviceId) {
        if (!deviceRepository.existsById(deviceId)) {
            throw new ResourceNotFoundException("Device not found with id " + deviceId);
        }
        deviceRepository.deleteById(deviceId);
        return true;
    }
}
