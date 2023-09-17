package com.practice.jobsearchproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.practice.jobsearchproject.model.entity.Role;
import com.practice.jobsearchproject.repository.RoleRepository;
import com.practice.jobsearchproject.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;

public class RoleServiceImplTest {

    @Test
    void testGetAllRoles() {
        RoleRepository roleRepository = mock(RoleRepository.class);

        RoleService roleService = new RoleServiceImpl(roleRepository);

        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");
        List<Role> roles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> result = roleService.getAllRoles();

        assertEquals(roles, result);
    }

    @Test
    void testFindByName() {
        RoleRepository roleRepository = mock(RoleRepository.class);

        RoleService roleService = new RoleServiceImpl(roleRepository);

        String roleName = "ROLE_USER";
        Role role = new Role(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(role);

        Role result = roleService.findByName(roleName);

        assertEquals(role, result);
    }
}

