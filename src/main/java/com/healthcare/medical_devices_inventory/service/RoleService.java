package com.healthcare.medical_devices_inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.medical_devices_inventory.model.Role;
import com.healthcare.medical_devices_inventory.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Create or update role
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Get role by ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Delete role by ID
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

