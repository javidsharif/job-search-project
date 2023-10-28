package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.VacancyRequestDto;
import com.practice.jobsearchproject.model.dto.response.DetailedPublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.DetailedVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.PublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.VacancyResponseDto;
import com.practice.jobsearchproject.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PublicVacancyResponseDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @GetMapping("/public/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedPublicVacancyResponseDto getPublicVacancyById(@PathVariable long id) {
        return vacancyService.getPublicVacancyById(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedVacancyResponseDto getVacancyById(@AuthenticationPrincipal CustomUserDetails companyDetails, @PathVariable long id) {
        return vacancyService.getVacancyById(id,companyDetails);
    }

    @GetMapping("/by/company")
    @ResponseStatus(HttpStatus.OK)
    public List<VacancyResponseDto> getAllVacanciesByCompany(@AuthenticationPrincipal CustomUserDetails companyDetails) {
        return vacancyService.getAllVacanciesByCompany(companyDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVacancy(@Valid @RequestBody VacancyRequestDto vacancyRequestDto, @AuthenticationPrincipal CustomUserDetails companyDetails) {
        vacancyService.createVacancy(vacancyRequestDto, companyDetails);
    }

    @PutMapping("/{vacancyId}")
    public void updateVacancy(@PathVariable long vacancyId, @Valid @RequestBody VacancyRequestDto vacancyRequestDto, @AuthenticationPrincipal CustomUserDetails companyDetails) {
        vacancyService.updateVacancy(vacancyId, vacancyRequestDto, companyDetails);
    }

    @DeleteMapping("/{vacancyId}")
    public void deleteVacancy(@PathVariable long vacancyId) {
        vacancyService.deleteVacancy(vacancyId);
    }
}
