package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.Company;
import com.practice.jobsearchproject.model.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findAllByCompany(Company company);
}
