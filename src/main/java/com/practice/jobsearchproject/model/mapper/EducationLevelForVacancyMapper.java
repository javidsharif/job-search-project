package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.EducationLevelForVacancyResponseDto;
import com.practice.jobsearchproject.model.entity.EducationLevelForVacancy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EducationLevelForVacancyMapper {
    EducationLevelForVacancyResponseDto convertToEducationLevelForVacancyResponseDto(EducationLevelForVacancy educationLevelForVacancy);

    EducationLevelForVacancy convertToEducationLevelForVacancy(EducationLevelForVacancyResponseDto educationLevelForVacancyResponseDto);
}
