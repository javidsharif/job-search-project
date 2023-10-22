package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    void deleteAllByResumeId(Long id);

    List<WorkExperience> findAllByResumeId(Long id);
}
