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
public class DetailedPublicVacancyResponseDto {
    private String vacancyName;
    private Set<CityResponseDto> cityResponseDtos;
    private String applicantName;
    private Double fromSalary;
    private CategoryResponseDto categoryResponseDto;
    private Set<JobTypeResponseDto> jobTypeResponseDtos;
    private Set<SpecialKnowledgeResponseDto> specialKnowledgeResponseDtos;
    private String textOfVacancy;
    private String specialRequirements;
    private String forApply;
    private Integer minimalWorkExperience;
    private EducationLevelResponseDto educationLevelResponseDto;
    private long viewNumber;
    private LocalDate createdAt;
}
