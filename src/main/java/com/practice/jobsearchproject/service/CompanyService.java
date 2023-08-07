package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.request.CompanyRequestDto;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    List<CompanyResponseDto> getAllCompanies();

    void createCompany(CompanyRequestDto companyRequestDto);

    void updateCompany(CompanyDto companyDto, CustomUserDetails customUserDetails);
}
