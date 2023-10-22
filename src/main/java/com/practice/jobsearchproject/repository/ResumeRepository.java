package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findAllByUserId(Long id);
}
