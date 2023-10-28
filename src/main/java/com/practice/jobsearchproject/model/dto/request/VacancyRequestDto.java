package com.practice.jobsearchproject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyRequestDto {
    private String applicantName;
    private String telephone;
    private String email;
    private Integer communicationToolId;
    @NotNull
    private String vacancyName;
    private Integer categoryId;
    private boolean isInternship;
    private Set<Integer> cityIds;
    private String address;
    private Double fromSalary;
    private Double toSalary;
    private Integer currencyId;
    private Set<Integer> jobTypeIds;
    private LocalDate expiryDate;
    private Set<Integer> specialKnowledgeIds;
    @NotNull
    @NotBlank(message = "text of vacancy is mandatory")
    private String textOfVacancy;
    private String specialRequirements;
    private String forApply;
    private Integer minimalWorkExperience;
    private Integer educationLevelId;
    private boolean noteContactAtVacancy;
    private boolean postVacancy;
}
