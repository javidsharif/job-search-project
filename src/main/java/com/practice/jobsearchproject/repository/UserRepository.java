package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
