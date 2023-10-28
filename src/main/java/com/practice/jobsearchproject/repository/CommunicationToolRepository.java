package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.CommunicationTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationToolRepository extends JpaRepository<CommunicationTool, Integer> {
    CommunicationTool findCommunicationToolById(Integer id);
}

