package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.request.CompanyRequestDto;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyResponseDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@Valid @RequestPart("companyRequestDto") CompanyRequestDto companyRequestDto, @RequestPart("file") MultipartFile file) throws IOException {
        companyService.createCompany(companyRequestDto, file);
    }

    @PutMapping
    public void updateCompany(@Valid @RequestPart("companyDto") CompanyDto companyDto,
                              @RequestPart("file") MultipartFile file,
                              @AuthenticationPrincipal CustomUserDetails companyDetails) throws IOException {
        companyService.updateCompany(companyDto, file, companyDetails);
    }
}
