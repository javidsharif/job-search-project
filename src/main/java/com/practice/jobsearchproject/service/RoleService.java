package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role findByName(String name);
}
