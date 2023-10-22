package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.AchievementAndAward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementAndAwardRepository extends JpaRepository<AchievementAndAward, Long> {
    void deleteAllByResumeId(Long id);
    List<AchievementAndAward> findAllByResumeId(Long id);
}
