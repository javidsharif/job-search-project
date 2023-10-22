package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    void deleteAllByResumeId(Long id);
    List<Language> findAllByResumeId(Long id);
}
