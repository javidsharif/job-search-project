package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.PrefferedWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrefferedWorkRepository extends JpaRepository<PrefferedWork, Long> {
    void deleteByResumeId(Long id);
    PrefferedWork findByResumeId(Long id);
}
