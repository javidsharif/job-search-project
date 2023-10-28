package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.City;
import com.practice.jobsearchproject.model.entity.SpecialKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SpecialKnowledgeRepository extends JpaRepository<SpecialKnowledge, Integer> {
    Set<SpecialKnowledge> findSpecialKnowledgeByIdIn(Set<Integer> ids);
}