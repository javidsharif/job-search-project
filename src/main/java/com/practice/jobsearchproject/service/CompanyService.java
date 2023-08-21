package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.request.CompanyRequestDto;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CompanyService {
    List<CompanyResponseDto> getAllCompanies();

    void createCompany(CompanyRequestDto companyRequestDto, MultipartFile file) throws IOException;

    void updateCompany(CompanyDto companyDto, MultipartFile file, CustomUserDetails customUserDetails) throws IOException;
}
