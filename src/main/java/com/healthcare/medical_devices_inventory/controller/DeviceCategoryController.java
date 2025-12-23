package com.healthcare.medical_devices_inventory.controller;

import java.util.List;

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

import com.healthcare.medical_devices_inventory.model.DeviceCategory;
import com.healthcare.medical_devices_inventory.service.DeviceCategoryService;

@RestController
@RequestMapping("/api/deviceCategories")
public class DeviceCategoryController {

    private final DeviceCategoryService deviceCategoryService;

    public DeviceCategoryController(DeviceCategoryService deviceCategoryService) {
        this.deviceCategoryService = deviceCategoryService;
    }

    // Get all Device Categories
    @GetMapping
    public ResponseEntity<List<DeviceCategory>> getAllCategories() {
        List<DeviceCategory> categories = deviceCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get Device Category by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceCategory> getCategoryById(@PathVariable Long id) {
        DeviceCategory category = deviceCategoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // Create a new Device Category
    @PostMapping
    public ResponseEntity<DeviceCategory> createCategory(@RequestBody DeviceCategory deviceCategory) { 
        DeviceCategory createdCategory = deviceCategoryService.createCategory(deviceCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    // Update an existing Device Category
    @PutMapping("/{id}")
    public ResponseEntity<DeviceCategory> updateCategory(@PathVariable Long id, @RequestBody DeviceCategory deviceCategory) {
        DeviceCategory updatedCategory = deviceCategoryService.updateCategory(id, deviceCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a Device Category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        deviceCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}