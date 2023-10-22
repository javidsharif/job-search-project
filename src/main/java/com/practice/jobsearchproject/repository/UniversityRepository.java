package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
