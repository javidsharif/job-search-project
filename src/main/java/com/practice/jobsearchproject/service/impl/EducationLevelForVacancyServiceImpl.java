package com.practice.jobsearchproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.response.EducationLevelForVacancyResponseDto;
import com.practice.jobsearchproject.model.entity.EducationLevelForVacancy;
import com.practice.jobsearchproject.model.mapper.EducationLevelForVacancyMapper;
import com.practice.jobsearchproject.repository.EducationLevelForVacancyRepository;
import com.practice.jobsearchproject.service.EducationLevelForVacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class EducationLevelForVacancyServiceImpl implements EducationLevelForVacancyService {
    private final EducationLevelForVacancyRepository educationLevelForVacancyRepository;
    private final EducationLevelForVacancyMapper educationLevelForVacancyMapper;

    public void saveAllEducationLevelsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("education-levels.json");
            String[] educationLevels = objectMapper.readValue(file, String[].class);
            for (String level : educationLevels) {
                EducationLevelForVacancy educationLevelForVacancy = new EducationLevelForVacancy();
                educationLevelForVacancy.setLevel(level);
                educationLevelForVacancyRepository.save(educationLevelForVacancy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EducationLevelForVacancyResponseDto> getAllEducationLevelsForVacancy() {
        return educationLevelForVacancyRepository.findAll().stream()
                .map(educationLevelForVacancyMapper::convertToEducationLevelForVacancyResponseDto)
                .collect(Collectors.toList());
    }
}