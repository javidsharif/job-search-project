package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
