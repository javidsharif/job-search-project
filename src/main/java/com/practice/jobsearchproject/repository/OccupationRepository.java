package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {
}
