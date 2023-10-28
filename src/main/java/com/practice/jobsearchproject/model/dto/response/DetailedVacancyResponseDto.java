package com.practice.jobsearchproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedVacancyResponseDto {
    private String applicantName;
    private String telephone;
    private String email;
    private CommunicationToolResponseDto communicationToolResponseDto;
    private String vacancyName;
    private CategoryResponseDto categoryResponseDto;
    private boolean isInternship;
    private Set<CityResponseDto> cityResponseDtos;
    private String address;
    private Double fromSalary;
    private Double toSalary;
    private CurrencyResponseDto currencyResponseDto;
    private Set<JobTypeResponseDto> jobTypeResponseDtos;
    private LocalDate expiryDate;
    private Set<SpecialKnowledgeResponseDto> specialKnowledgeResponseDtos;
    private String textOfVacancy;
    private String specialRequirements;
    private String forApply;
    private Integer minimalWorkExperience;
    private EducationLevelResponseDto educationLevelResponseDto;
    private boolean noteContactAtVacancy;
    private boolean postVacancy;
}
