package com.healthcare.medical_devices_inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.repository.DeviceCategoryRepository;

@Service
public class DeviceCategoryService {
    
    private final DeviceCategoryRepository deviceCategoryRepository;

    public DeviceCategoryService(DeviceCategoryRepository deviceCategoryRepository){
        this.deviceCategoryRepository = deviceCategoryRepository;
    }

    public List<DeviceCategory> getAllCategories(){
        return deviceCategoryRepository.findAll();
    }

    public Optional<DeviceCategory> getCategoryById(Long categoryId){
        return deviceCategoryRepository.findById(categoryId);
    }

    public DeviceCategory createCategory(DeviceCategory deviceCategory){
        return deviceCategoryRepository.save(deviceCategory);
    }

    public DeviceCategory updateCategory(Long id,DeviceCategory deviceCategory){
        Optional<DeviceCategory> existingCaterory = getCategoryById(id);
        if(existingCaterory.isPresent()){
            DeviceCategory category = existingCaterory.get();
            category.setCategoryName(deviceCategory.getCategoryName());
            category.setDescription(deviceCategory.getDescription());
            category.setDevices(deviceCategory.getDevices());
            deviceCategoryRepository.save(category);
        }
        return null;
    }

    
}
