package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Integer> {
    Set<JobType> findJobTypeByIdIn(Set<Integer> ids);
}

