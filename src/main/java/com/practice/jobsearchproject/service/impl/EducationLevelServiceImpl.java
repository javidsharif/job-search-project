package com.practice.jobsearchproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.response.EducationLevelResponseDto;
import com.practice.jobsearchproject.model.entity.EducationLevel;
import com.practice.jobsearchproject.model.mapper.EducationLevelMapper;
import com.practice.jobsearchproject.repository.EducationLevelRepository;
import com.practice.jobsearchproject.service.EducationLevelService;
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
public class EducationLevelServiceImpl implements EducationLevelService {
    private final EducationLevelRepository educationLevelRepository;
    private final EducationLevelMapper educationLevelMapper;

    public void saveAllEducationLevelsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("education-levels.json");
            String[] educationLevels = objectMapper.readValue(file, String[].class);
            for (String level : educationLevels) {
                EducationLevel educationLevel = new EducationLevel();
                educationLevel.setLevel(level);
                educationLevelRepository.save(educationLevel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EducationLevelResponseDto> getAllEducationLevels() {
        return educationLevelRepository.findAll().stream()
                .map(educationLevelMapper::convertToEducationLevelResponseDto)
                .collect(Collectors.toList());
    }
}