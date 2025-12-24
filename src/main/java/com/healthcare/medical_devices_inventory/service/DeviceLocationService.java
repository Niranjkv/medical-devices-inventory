package com.healthcare.medical_devices_inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.repository.DeviceLocationRepository;

@Service
public class DeviceLocationService {

    private final DeviceLocationRepository deviceLocationRepository;

    public DeviceLocationService(DeviceLocationRepository deviceLocationRepository) {
        this.deviceLocationRepository = deviceLocationRepository;
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<DeviceLocation> getAllLocations() {
        return deviceLocationRepository.findAll();
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public DeviceLocation getLocationById(Long locationId) {
        return deviceLocationRepository.findById(locationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("DeviceLocation not found with id " + locationId));
    }

    // CREATE
    @Transactional
    public DeviceLocation createLocation(DeviceLocation deviceLocation) {
        return deviceLocationRepository.save(deviceLocation);
    }

    // UPDATE
    @Transactional
    public DeviceLocation updateLocation(Long id, DeviceLocation input) {

        DeviceLocation existing = getLocationById(id);

        existing.setLocationName(input.getLocationName());
        existing.setLocationType(input.getLocationType());
        existing.setDescription(input.getDescription());

        return deviceLocationRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public List<DeviceLocation> getFilteredLocations(String locationType) {
        if (locationType != null) {
            return deviceLocationRepository.findByLocationType(locationType);
        }
        return deviceLocationRepository.findAll(); // No filter, return all locations
    }

    // DELETE
    @Transactional
    public void deleteLocation(Long locationId) {

        if (!deviceLocationRepository.existsById(locationId)) {
            throw new ResourceNotFoundException(
                    "DeviceLocation not found with id " + locationId);
        }

        deviceLocationRepository.deleteById(locationId);
    }
}
