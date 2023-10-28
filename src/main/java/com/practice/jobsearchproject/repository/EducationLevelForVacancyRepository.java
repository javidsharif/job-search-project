package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.EducationLevelForVacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationLevelForVacancyRepository extends JpaRepository<EducationLevelForVacancy, Integer> {
    EducationLevelForVacancy findEducationLevelForVacancyById(Integer id);
}


