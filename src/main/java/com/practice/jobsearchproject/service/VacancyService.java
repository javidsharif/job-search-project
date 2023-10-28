package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.VacancyRequestDto;
import com.practice.jobsearchproject.model.dto.response.DetailedPublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.DetailedVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.PublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.VacancyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VacancyService {
    List<PublicVacancyResponseDto> getAllVacancies();

    DetailedPublicVacancyResponseDto getPublicVacancyById(long id);

    DetailedVacancyResponseDto getVacancyById(long id, CustomUserDetails companyDetails);

    List<VacancyResponseDto> getAllVacanciesByCompany(CustomUserDetails companyDetails);

    void createVacancy(VacancyRequestDto vacancyRequestDto, CustomUserDetails customUserDetails);

    void updateVacancy(long vacancyId, VacancyRequestDto userDto, CustomUserDetails userDetails);

    void deleteVacancy(long vacancyId);
}
