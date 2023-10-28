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
public class PublicVacancyResponseDto {
    private long id;
    private String vacancyName;
    private String applicantName;
    private long viewNumber;
    private Set<CityResponseDto> cityResponseDtos;
    private Double fromSalary;
    private Double toSalary;
    private Set<JobTypeResponseDto> jobTypeResponseDtos;
    private LocalDate createdAt;
}
