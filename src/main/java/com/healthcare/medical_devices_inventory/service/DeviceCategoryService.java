package com.healthcare.medical_devices_inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.exception.ResourceNotFoundException;
import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.repository.DeviceCategoryRepository;

@Service
public class DeviceCategoryService {

    private final DeviceCategoryRepository deviceCategoryRepository;

    public DeviceCategoryService(DeviceCategoryRepository deviceCategoryRepository) {
        this.deviceCategoryRepository = deviceCategoryRepository;
    }

    // Get all Device Categories
    @Transactional(readOnly = true)
    public List<DeviceCategory> getAllCategories() {
        return deviceCategoryRepository.findAll();
    }

    // Get Device Category by ID
    @Transactional(readOnly = true)
    public DeviceCategory getCategoryById(Long categoryId) {
        return deviceCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
    }

    // Create a new Device Category
    @Transactional
    public DeviceCategory createCategory(DeviceCategory deviceCategory) {
        return deviceCategoryRepository.save(deviceCategory);
    }

    // Update an existing Device Category
    @Transactional
    public DeviceCategory updateCategory(Long id, DeviceCategory deviceCategory) {
        DeviceCategory existingCategory = deviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

        existingCategory.setCategoryName(deviceCategory.getCategoryName());
        existingCategory.setDescription(deviceCategory.getDescription());
        existingCategory.setDevices(deviceCategory.getDevices());
        return deviceCategoryRepository.save(existingCategory); // Save the updated category
    }

    // Delete a Device Category by ID
    @Transactional
    public void deleteCategory(Long id) {
        if (!deviceCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        deviceCategoryRepository.deleteById(id);
    }
}
