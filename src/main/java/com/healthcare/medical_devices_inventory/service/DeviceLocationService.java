package com.healthcare.medical_devices_inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthcare.medical_devices_inventory.model.DeviceLocation;
import com.healthcare.medical_devices_inventory.repository.DeviceLocationRepository;

@Service
public class DeviceLocationService {
    
    private final DeviceLocationRepository deviceLocationRepository;

    public DeviceLocationService(DeviceLocationRepository deviceLocationRepository){
        this.deviceLocationRepository = deviceLocationRepository;
    }

    // Get all device locations
    public List<DeviceLocation> getAllLocations(){
        return deviceLocationRepository.findAll();
    }

    // Get device location by id
    public Optional<DeviceLocation> getLocationById(Long locationId){
        return deviceLocationRepository.findById(locationId);
    }

    // Create a new device location
    public DeviceLocation createLocation(DeviceLocation deviceLocation){
        return deviceLocationRepository.save(deviceLocation);
    }

    // Update an existing device location
    public DeviceLocation updateLocation(Long id, DeviceLocation deviceLocation){
        Optional<DeviceLocation> existingLocation = getLocationById(id);
        if(existingLocation.isPresent()){
            DeviceLocation location = existingLocation.get();
            location.setName(deviceLocation.getName());
            location.setDescription(deviceLocation.getDescription());
            location.setCreatedAt(deviceLocation.getCreatedAt());
            location.setUpdatedAt(deviceLocation.getUpdatedAt());
            deviceLocationRepository.save(location);
            return location; // return updated location
        }
        return null; // return null if the location with the provided id doesn't exist
    }

    // Delete a device location
    public boolean deleteLocation(Long locationId){
        Optional<DeviceLocation> existingLocation = getLocationById(locationId);
        if(existingLocation.isPresent()){
            deviceLocationRepository.deleteById(locationId);
            return true; // return true if deletion was successful
        }
        return false; // return false if location with the provided id doesn't exist
    }
}
