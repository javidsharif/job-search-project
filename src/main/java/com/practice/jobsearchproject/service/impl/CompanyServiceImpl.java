package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.config.JwtTokenUtil;
import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.request.CompanyRequestDto;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.CompanyAuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.model.dto.response.UserAuthenticationResponse;
import com.practice.jobsearchproject.model.entity.Company;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.repository.CompanyRepository;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.service.CompanyService;
import com.practice.jobsearchproject.service.RoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        log.info("Fetching all companies");
        return companies.stream()
                .map(companyMapper::convertToCompanyResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthenticationResponse createCompany(CompanyRequestDto companyRequestDto) {
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
        companyRepository.save(company);
        userAuthRepository.save(userAuth);
        log.info("Creating new company {}", company.getName());

        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(companyRequestDto.getEmail());
        final var token = jwtTokenUtil.generateToken(userDetails);
        return CompanyAuthenticationResponse.builder().token(token).company(companyMapper.convertToCompanyResponseDto(company)).build();
    }

    @Override
    public void updateCompany(CompanyDto companyDto, CustomUserDetails companyDetails) {
        String newPassword = companyDto.getPassword();
        String confirmedPassword = companyDto.getConfirmPassword();
        Company authenticatedCompany = findByEmail(companyDetails.getUsername()).getCompany();
        if (!newPassword.equals(confirmedPassword)) {
            throw new PasswordException("The password confirmation does not match.");
        }
        if (!newPassword.equals(authenticatedCompany.getUserAuthentication().getPassword())) {
            authenticatedCompany.getUserAuthentication().setPassword(passwordEncoder.encode(newPassword));
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
                .photoUrl(companyRequestDto.getPhotoUrl())
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
        if (!authenticatedCompany.getUserAuthentication().getPassword().equals(companyDto.getPassword())) {
            authenticatedCompany.getUserAuthentication().setPassword(passwordEncoder.encode(companyDto.getPassword()));
        }
        authenticatedCompany.setTelephone(companyDto.getTelephone());
        authenticatedCompany.setCvEmail(companyDto.getCvEmail());
        authenticatedCompany.setInformation(companyDto.getInformation());
        authenticatedCompany.setPhotoUrl(companyDto.getPhotoUrl());
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
