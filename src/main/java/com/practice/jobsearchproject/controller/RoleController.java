package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.RoleDto;
import com.practice.jobsearchproject.model.mapper.RoleMapper;
import com.practice.jobsearchproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping
    public List<RoleDto> getAllRoles() {
       return roleService.getAllRoles().stream().map(roleMapper::toRoleDto).collect(Collectors.toList());
    }

}
