package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.DetailedPublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.DetailedVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.PublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.VacancyResponseDto;
import com.practice.jobsearchproject.model.entity.Vacancy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VacancyMapper {
    VacancyResponseDto convertToVacancyResponseDto(Vacancy vacancy);
    PublicVacancyResponseDto convertToPublicVacancyResponseDto(Vacancy vacancy);
    DetailedPublicVacancyResponseDto convertToDetailedPublicVacancyResponseDto(Vacancy vacancy);
    DetailedVacancyResponseDto convertToDetailedVacancyResponseDto(Vacancy vacancy);
}
