package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.config.security.jwt.JwtTokenUtil;
import com.practice.jobsearchproject.config.security.service.CustomUserDetailsService;
import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.request.CompanyRequestDto;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.model.entity.Company;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.repository.CompanyRepository;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.service.CompanyService;
import com.practice.jobsearchproject.service.FileService;
import com.practice.jobsearchproject.service.RoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserAuthenticationRepository userAuthRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final FileService fileService;

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        log.info("Fetching all companies");
        return companies.stream()
                .map(companyMapper::convertToCompanyResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createCompany(CompanyRequestDto companyRequestDto, MultipartFile file) throws IOException {
        if (userAuthRepository.findByEmail(companyRequestDto.getEmail()).isPresent()) {
            throw new AlreadyExistsException(String.format("email with %s already exists", companyRequestDto.getEmail()));
        }
        if (!companyRequestDto.getPassword().equals(companyRequestDto.getConfirmPassword())) {
            throw new PasswordException("Password is wrong");
        }
        Company company = getCompany(companyRequestDto);
        UserAuthentication userAuth = getUserAuthentication(companyRequestDto.getEmail(), companyRequestDto.getPassword(), company);
        company.setUserAuthentication(userAuth);
        userAuth.setCompany(company);
        company.setRole(roleService.findByName("USER"));
        if (file != null && !file.isEmpty()) {
            fileService.uploadFile(file, company);
        }
        companyRepository.save(company);
        userAuthRepository.save(userAuth);
        log.info("Creating new company {}", company.getName());
    }

    @Override
    public void updateCompany(CompanyDto companyDto, MultipartFile file, CustomUserDetails companyDetails) throws IOException {
        String newPassword = companyDto.getPassword();
        String confirmedPassword = companyDto.getConfirmPassword();
        Company authenticatedCompany = findByEmail(companyDetails.getUsername()).getCompany();
        if (!newPassword.equals(confirmedPassword)) {
            throw new PasswordException("The password confirmation does not match.");
        }
        if (!newPassword.equals(authenticatedCompany.getUserAuthentication().getPassword())) {
            authenticatedCompany.getUserAuthentication().setPassword(passwordEncoder.encode(newPassword));
        }
        if (file != null && !file.isEmpty()) {
            Optional.ofNullable(authenticatedCompany.getPhotoUrl())
                    .filter(photoUrl -> !photoUrl.isEmpty())
                    .map(photoUrl -> photoUrl.substring(photoUrl.lastIndexOf('/') + 1))
                    .ifPresent(fileService::deleteFile);

            fileService.uploadFile(file, authenticatedCompany);
        }

        fillCompany(companyDto, authenticatedCompany);
        companyRepository.save(authenticatedCompany);
        log.info("Updating the company {}", authenticatedCompany.getName());
    }

    private Company getCompany(CompanyRequestDto companyRequestDto) {
        return Company.builder()
                .name(companyRequestDto.getName())
                .telephone(companyRequestDto.getTelephone())
                .cvEmail(companyRequestDto.getCvEmail())
                .information(companyRequestDto.getInformation())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private UserAuthentication getUserAuthentication(String email, String password, Company company) {
        return new UserAuthentication(email, passwordEncoder.encode(password), company);
    }

    public UserAuthentication findByEmail(String email) {
        return userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("email with %s not found", email)));
    }

    private void fillCompany(CompanyDto companyDto, Company authenticatedCompany) {
        authenticatedCompany.setName(companyDto.getName());
        if (!authenticatedCompany.getUserAuthentication().getEmail().equals(companyDto.getEmail())) {
            authenticatedCompany.getUserAuthentication().setEmail(companyDto.getEmail());
        }
        authenticatedCompany.setTelephone(companyDto.getTelephone());
        authenticatedCompany.setCvEmail(companyDto.getCvEmail());
        authenticatedCompany.setInformation(companyDto.getInformation());
        authenticatedCompany.setCity(companyDto.getCity());
        authenticatedCompany.setFieldOfActivity(companyDto.getFieldOfActivity());
        authenticatedCompany.setFoundationDate(companyDto.getFoundationDate());
        authenticatedCompany.setNumberOfEmployees(companyDto.getNumberOfEmployees());
        authenticatedCompany.setAddress(companyDto.getAddress());
        authenticatedCompany.setSiteOfCompany(companyDto.getSiteOfCompany());
        authenticatedCompany.setFacebookProfileLink(companyDto.getFacebookProfileLink());
        authenticatedCompany.setInstagramProfileLink(companyDto.getInstagramProfileLink());
        authenticatedCompany.setTwitterProfileLink(companyDto.getTwitterProfileLink());
        authenticatedCompany.setLinkedinProfileLink(companyDto.getLinkedinProfileLink());
    }
}
