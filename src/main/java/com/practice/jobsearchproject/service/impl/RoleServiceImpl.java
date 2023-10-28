package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.model.entity.Role;
import com.practice.jobsearchproject.repository.RoleRepository;
import com.practice.jobsearchproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
