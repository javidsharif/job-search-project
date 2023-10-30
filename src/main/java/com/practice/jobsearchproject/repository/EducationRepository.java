package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    void deleteAllByResumeId(Long id);
    List<Education> findAllByResumeId(Long id);
}
