package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
