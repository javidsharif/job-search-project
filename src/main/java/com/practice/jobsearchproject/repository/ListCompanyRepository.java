package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.ListCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListCompanyRepository extends JpaRepository<ListCompany, Long> {
}
