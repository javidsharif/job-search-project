package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.EducationLevelForVacancyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EducationLevelForVacancyService {
    List<EducationLevelForVacancyResponseDto> getAllEducationLevelsForVacancy();
}
