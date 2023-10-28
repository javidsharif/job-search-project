package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, Integer> {
    EducationLevel findEducationLevelById(Integer id);
}

