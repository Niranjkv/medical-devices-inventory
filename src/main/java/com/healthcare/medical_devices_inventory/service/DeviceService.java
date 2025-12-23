package com.healthcare.medical_devices_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.dto.DeviceDTO;
import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.Device;
import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.repository.DeviceCategoryRepository;
import com.healthcare.medical_devices_inventory.repository.DeviceRepository;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, DeviceCategoryRepository deviceCategoryRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceCategoryRepository = deviceCategoryRepository;
    }

    @Transactional
    public Device createDevice(DeviceDTO deviceDTO) {
        // Fetch the DeviceCategory from the database using the categoryId from the DTO
        DeviceCategory category = deviceCategoryRepository.findById(deviceDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + deviceDTO.getCategoryId()));

        // Create a new Device entity and map values from the DTO
        Device device = new Device();
        device.setModelNumber(deviceDTO.getModelNumber());
        device.setSerialNumber(deviceDTO.getSerialNumber());
        device.setDescription(deviceDTO.getDescription());
        device.setQuantity(deviceDTO.getQuantity());
        device.setRegistrationDate(deviceDTO.getRegistrationDate());
        device.setMaintanenceDate(deviceDTO.getMaintenanceDate());
        device.setCategory(category);  // Set the fetched category to the device

        // Save the device and return it
        return deviceRepository.save(device);
    }

    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId));
    }

    @Transactional
    public Device updateDevice(Long deviceId, DeviceDTO deviceDTO) {
        Device device = getDeviceById(deviceId);  // Fetch the device by its ID

        // Update the fields from the DTO if they are not null
        if (deviceDTO.getModelNumber() != null) {
            device.setModelNumber(deviceDTO.getModelNumber());
        }
        if (deviceDTO.getSerialNumber() != null) {
            device.setSerialNumber(deviceDTO.getSerialNumber());
        }
        if (deviceDTO.getDescription() != null) {
            device.setDescription(deviceDTO.getDescription());
        }
        if (deviceDTO.getQuantity() != null) {
            device.setQuantity(deviceDTO.getQuantity());
        }
        if (deviceDTO.getRegistrationDate() != null) {
            device.setRegistrationDate(deviceDTO.getRegistrationDate());
        }
        if (deviceDTO.getMaintenanceDate() != null) {
            device.setMaintanenceDate(deviceDTO.getMaintenanceDate());
        }

        // Update the category if a new categoryId is provided
        if (deviceDTO.getCategoryId() != null) {
            DeviceCategory category = deviceCategoryRepository.findById(deviceDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + deviceDTO.getCategoryId()));
            device.setCategory(category);
        }

        // Save and return the updated device
        return deviceRepository.save(device);
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
