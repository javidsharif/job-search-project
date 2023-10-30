package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {
    void deleteAllByResumeId(Long id);
    List<Knowledge> findAllByResumeId(Long id);
}
